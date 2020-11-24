package com.zykj.samplechat.model;

import java.io.Serializable;

/**
 * Created by ninos on 2016/7/27.
 */
public class mqunlb implements Serializable {

        /**
         * Id : b8851c49-96e2-4d96-bfa2-a966013463b9
         * ImagePath : null
         * Max : 111
         * Min : 80
         * Name : yfhhhh
         * Odds : 12
         * RedCount : 12
         * State : 0
         * teamAnnouncement : 1212
         * teamIntroduce : 121212
         * Type : 0
         */

        private String Id;
        private String ImagePath;
        private Double Max;
        private Double Min;
        private String Name;

    public String getTeamCode() {
        return TeamCode;
    }

    public void setTeamCode(String teamCode) {
        TeamCode = teamCode;
    }

    public String TeamCode;

    public double getOdds() {
        return Odds;
    }

    public void setOdds(double odds) {
        Odds = odds;
    }

    private double Odds;
        private int RedCount;
        private int State;
        private String teamAnnouncement;
        private String teamIntroduce;
        private int Type;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String ImagePath) {
            this.ImagePath = ImagePath;
        }

        public Double getMax() {
            return Max;
        }

        public void setMax(Double Max) {
            this.Max = Max;
        }

        public Double getMin() {
            return Min;
        }

        public void setMin(Double Min) {
            this.Min = Min;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getRedCount() {
            return RedCount;
        }

        public void setRedCount(int RedCount) {
            this.RedCount = RedCount;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getTeamAnnouncement() {
            return teamAnnouncement;
        }

        public void setTeamAnnouncement(String teamAnnouncement) {
            this.teamAnnouncement = teamAnnouncement;
        }

        public String getTeamIntroduce() {
            return teamIntroduce;
        }

        public void setTeamIntroduce(String teamIntroduce) {
            this.teamIntroduce = teamIntroduce;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }
    }

