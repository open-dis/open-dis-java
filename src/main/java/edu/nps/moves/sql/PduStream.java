package edu.nps.moves.sql;

import java.util.*;
import javax.persistence.*;
import edu.nps.moves.dis.*;

/**
 * Describes one stream of PDUs. If we're staving PDUs from the network into a
 * database, one danger is that we put data from several exercises into a single
 * table. There would be no way to tell which PDUs came from what exercise.
 * (Theoretically the exerciseID could be used for this, but it wouldn't work
 * well for various reasons.)
 * <p>
 * The PDU stream has an identifier which can be used as a foreign key in the
 * PDU header, via a special, non-DIS addition to the PDU header that is not
 * marshalled to IEEE packets. This ID refers to an entry in this table, which
 * describes the name of the stream being recorded, the start time (in local
 * wall clock time) and the end time (ditto). With this information you can
 * extract all the PDUs related to the exercise from the table of PDUs, once
 * you select the PDU stream from this table.<p>
 * 
 * @author DMcG
 */
@Entity
public class PduStream
{
    /** Primary key  of the table */
    private long pk_PduStream;

    /** End time of the recording, in local wall clock time. */
    private String exerciseName;
    private Date wallClockStartTime;
    private Date wallClockEndTime;

    /**
     * Primary key  of the table
     * @return the pk_PduStream
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getPk_PduStream() {
        return pk_PduStream;
    }

    /**
     * Primary key  of the table
     * @param pk_PduStream the pk_PduStream to set
     */
    public void setPk_PduStream(long pk_PduStream) {
        this.pk_PduStream = pk_PduStream;
    }

    /**
     * Exercise name; this is what should be presented to the user
     * @return the exerciseName
     */
    @Basic
    public String getExerciseName() {
        return exerciseName;
    }

    /**
     * Exercise name; this is what should be presented to the user
     * @param exerciseName the exerciseName to set
     */
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    /**
     * Start time of the recording, in local wall clock time.
     * @return the wallClockStartTime
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getWallClockStartTime() {
        return wallClockStartTime;
    }

    /**
     * Start time of the recording, in local wall clock time.
     * @param wallClockStartTime the wallClockStartTime to set
     */
    public void setWallClockStartTime(Date wallClockStartTime) {
        this.wallClockStartTime = wallClockStartTime;
    }

    /**
     * End time of the recording, in local wall clock time.
     * @return the wallClockEndTime
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getWallClockEndTime() {
        return wallClockEndTime;
    }

    /**
     * End time of the recording, in local wall clock time.
     * @param wallClockEndTime the wallClockEndTime to set
     */
    public void setWallClockEndTime(Date wallClockEndTime) {
        this.wallClockEndTime = wallClockEndTime;
    }

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
