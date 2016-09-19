package Data;
import java.util.Date;

public class SerailData {
    
    private String userID;
    private java.util.Date date;
    //Data is data(voltage) at this point
    private double data;
    private int sampleNo;
    //private int beatNumber;
    //private boolean currentBeatNormal;
    
    public SerailData(String userID, Date date, double d, int sampleNo) {
        super();
        this.userID = userID;
        this.date = date;
        this.sampleNo = sampleNo;
        this.data = d;
        //this.beatNumber = beatNumber;
        //this.currentBeatNormal = currentBeatNormal;
    }
    
    public String getUserID(){
        return this.userID;
    }
    
    public java.util.Date getDate(){
        return this.date;
    }
    
    //get voltage
    public double getData(){
        return this.data;
    }
    
    public int getSampleNo(){
        return this.sampleNo;
    }
    //public int getBeatNumber(){
    //    return this.beatNumber;
    //}
    
    //public boolean getCurrentBeatNormal(){
    //    return this.currentBeatNormal;
    //}
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        //result = prime * result + beatNumber;
        //result = prime * result + (currentBeatNormal ? 1231 : 1237);
        result = (int) (prime * result + Double.doubleToLongBits(data));
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((userID == null) ? 0 : userID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SerailData other = (SerailData) obj;
        //if (beatNumber != other.beatNumber)
        //    return false;
        //if (currentBeatNormal != other.currentBeatNormal)
        //    return false;
        if (Float.floatToIntBits((float) data) != Float.floatToIntBits((float) other.data))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (userID == null) {
            if (other.userID != null)
                return false;
        } else if (!userID.equals(other.userID))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "SerailData [userID=" + userID + ", date=" + date + ", data=" + data +
                "]";
    }
}
