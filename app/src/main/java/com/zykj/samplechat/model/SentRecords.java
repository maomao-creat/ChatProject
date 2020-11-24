package com.zykj.samplechat.model;

import com.zykj.samplechat.utils.StringUtil;

/**
 * Created by ninos on 2017/8/22.发红包记录
 */

public class SentRecords {

        /**
         * Id : 181414
         * RecvieId : 0
         * NicName :
         * PhotoPath :
         * Description : 7
         * Totalmoney : 140
         * RecMoney : 0
         * PayTime : 2018-03-24 17:03:58
         * TimeOrderNo : 41
         * TeamId : 569
         * TeamName : 朋友社交【可发可抢】
         * IsLei : 0
         */
//1发包
        private int Id;
        private int RecvieId;
        private String NicName;
        private String Amount;
        private String PhotoPath;
        private String Description;
        private String Totalmoney;
        private String RecMoney;
        private String PayTime;
        private int TimeOrderNo;
        private int TeamId;
        private String TeamName;
        private String PeifuMoney;
        private String TimeStamp;
        private String Value;
        private String value;
        private String ReceivedNicName;
        private String ReceivedPhotoPath;
        private String SentNicName;
        private String ZhongjiangMoney;
        private String SentPhotoPath;
        private int IsLei;

    public String getZhongjiangMoney() {
        return ZhongjiangMoney;
    }

    public void setZhongjiangMoney(String zhongjiangMoney) {
        ZhongjiangMoney = zhongjiangMoney;
    }

    public String getReceivedNicName() {
        return ReceivedNicName;
    }

    public void setReceivedNicName(String receivedNicName) {
        ReceivedNicName = receivedNicName;
    }

    public String getReceivedPhotoPath() {
        return ReceivedPhotoPath;
    }

    public void setReceivedPhotoPath(String receivedPhotoPath) {
        ReceivedPhotoPath = receivedPhotoPath;
    }

    public String getSentNicName() {
        return SentNicName;
    }

    public void setSentNicName(String sentNicName) {
        SentNicName = sentNicName;
    }

    public String getSentPhotoPath() {
        return SentPhotoPath;
    }

    public void setSentPhotoPath(String sentPhotoPath) {
        SentPhotoPath = sentPhotoPath;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getValue() {
        if(StringUtil.isEmpty(Value)){
            return value;
        }
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
    public void setvalue(String value) {
        this.value = value;
    }

    public String getPeifuMoney() {
        return PeifuMoney;
    }

    public void setPeifuMoney(String peifuMoney) {
        PeifuMoney = peifuMoney;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getRecvieId() {
            return RecvieId;
        }

        public void setRecvieId(int RecvieId) {
            this.RecvieId = RecvieId;
        }

        public String getNicName() {
            return NicName;
        }

        public void setNicName(String NicName) {
            this.NicName = NicName;
        }

        public String getPhotoPath() {
            return PhotoPath;
        }

        public void setPhotoPath(String PhotoPath) {
            this.PhotoPath = PhotoPath;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getTotalmoney() {
            return Totalmoney;
        }

        public void setTotalmoney(String Totalmoney) {
            this.Totalmoney = Totalmoney;
        }

        public String getRecMoney() {
            return RecMoney;
        }

        public void setRecMoney(String RecMoney) {
            this.RecMoney = RecMoney;
        }

        public String getPayTime() {
            return PayTime;
        }

        public void setPayTime(String PayTime) {
            this.PayTime = PayTime;
        }

        public int getTimeOrderNo() {
            return TimeOrderNo;
        }

        public void setTimeOrderNo(int TimeOrderNo) {
            this.TimeOrderNo = TimeOrderNo;
        }

        public int getTeamId() {
            return TeamId;
        }

        public void setTeamId(int TeamId) {
            this.TeamId = TeamId;
        }

        public String getTeamName() {
            return TeamName;
        }

        public void setTeamName(String TeamName) {
            this.TeamName = TeamName;
        }

        public int getIsLei() {
            return IsLei;
        }

        public void setIsLei(int IsLei) {
            this.IsLei = IsLei;
        }

}
