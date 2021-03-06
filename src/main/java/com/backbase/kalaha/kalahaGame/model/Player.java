package com.backbase.kalaha.kalahaGame.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author afatima
 * Player of the board
 */
@EqualsAndHashCode(of = {"playerId"})
public class Player {

    private final static int FIRST_PLAYER_PIT_STARTING_INDEX = 1;
    private final static int SECOND_PLAYER_PIT_STARTING_INDEX = 8;

    private final static int FIRST_PLAYER_KALAH_ID = 7;
    private final static int SECOND_PLAYER_KALAH_ID = 14;

    public final static int STONES_PER_PIT = 6;

    public enum Name {
        FIRST_PLAYER("First Player"),
        SECOND_PLAYER("Second Player");

        String name;

        Name(String name) {
            this.name = name;
        }
    }

    @Getter
    @JsonIgnore
    private String playerId;

    @Getter
    @Setter
    @JsonIgnore
    private List<Pit> pits;

    @Getter
    @Setter
    private Pit kalah;

    @Getter
    @Setter
    private Name name;

    public Player(Name name) {
        this.playerId = UUID.randomUUID().toString();
        this.name = name;
        assignPitsAndKalah();
    }

    public Pit getPitById(int id) {
        for (Pit pit : pits) {
            if (pit.getPitId() == id) {
                return pit;
            }

        }


        return null;
    }


    private void assignPitsAndKalah() {
        //initialize pits
        pits = new ArrayList<Pit>();

        //assign pits
        for (int i = pitsStartingIndex(); i <= pitsEndingIndex(); i++) {
            Pit pit = new Pit(i, STONES_PER_PIT);
            pits.add(pit);
        }

        //assign kalah
        //no need for position on kalah
        kalah = new Pit(isFirstPlayer() ? FIRST_PLAYER_KALAH_ID : SECOND_PLAYER_KALAH_ID, 0);
    }


    public boolean isFirstPlayer() {
        return name == name.FIRST_PLAYER;
    }


    public int addStoneToKalahAndReturnRemaining(int stones) {
        this.kalah.addStone();
        return --stones;
    }

    public int pitsStartingIndex() {
        return this.isFirstPlayer() ? FIRST_PLAYER_PIT_STARTING_INDEX : SECOND_PLAYER_PIT_STARTING_INDEX;
    }

    public int pitsEndingIndex() {
        return pitsStartingIndex() + 5;
    }

    public boolean canAddStoneToPit(int pitId) {
        return (pitId <= pitsEndingIndex()) ? true : false;
    }
}
