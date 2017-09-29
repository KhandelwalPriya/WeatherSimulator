import java.util.*;
import java.text.*;

public class WeatherSimulator {

    private static final String[][] DATA = {
        {"Bangalore","12.9716 N 77.5946 E","20","27","800","990","65","80"}, 
        {"Mumbai","19.0760 N 72.8777 E","25","32","998","1010","80","90"},
        {"Chennai","13.0827 N 80.2707 E","27","34","1100","1190","85","98"},
        {"Delhi","28.7041 N 77.1025 E","31","38","1020","1120","70","80"}
    };
    
    public static void main(String args[]) {
        
        WeatherSimulator simulator = new WeatherSimulator();
            for (String[] data : DATA){
            System.out.println(simulator.getCityWeather(data).toString());
        }
        
    }
    
    private CityWeather getCityWeather(String[] data){
        
        /* getting current date and time */
        
        Calendar calendar = Calendar.getInstance();
        int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String date = sdf.format(calendar.getTime());
        
        /* Storing results from functions */
        
        float temp = getTemp(hourOfDay, Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        float press = getPressure(hourOfDay, Float.parseFloat(data[4]), Float.parseFloat(data[5]));
        int humid = getHumidity(hourOfDay, Integer.parseInt(data[6]), Integer.parseInt(data[7]));
        String condition = getCondition(temp);
        
        /* returning object of class CityWeather */
        
        return  new CityWeather(data[0], date, data[1], temp, press, humid, condition);
    }
    
    /* Calculating current tempreture using current time, minimum and maximum tempreture of the place 
       considering maximum tempreture at 12 noon and minimum tempreture at 12 midnight
       as the hours increses from midnight to noon, tempreature will raise towards maximum
       once it touches maximum, it begins to decrese to it's minimum value 
       so if time is less than 12 noon, we are calculating change in tempreture in number of hours and adding minimum tempreture to it
       if time is more than 12 noon, we will substract change in tempreture in number of hours from maximum tempreture. */
    
    
    private  float getTemp(int hour, float minTemp, float maxTemp){
        if (hour <= 12){
            return ((maxTemp-minTemp)/12)*hour + minTemp;
        }else {
            return maxTemp - ((maxTemp-minTemp)/12)*(hour-12);
        }
    }
    
    /* we are calculating weather condition using current tempreture, if the current tempreture is
          less than 24 degrees, condition would be rainy
          more than or equal to 24 degrees, condition would be sunny*/
    private  String getCondition(float temp){   
          return temp < 24 ? "rainy" : "sunny";
    }
    
    /* we are using time, minimum pressure and maximum pressure to calculate pressure at current time
     * if hour or time is greater than 12 noon we will calculate change in pressure in one hour multiplied with
     difference in hours and add minimum pressure to it
     * if time is less than 12 noon, we are substracting change in pressure in one hour from maximum pressure */
    
    private  float getPressure(int hour, float minPres, float maxPres){
        if (hour > 12){
            return ((maxPres-minPres)/12)*(hour-12) + minPres;
        }else {
            return maxPres - ((maxPres-minPres)/12)*hour;
        }
    }
    
        /* we are using time, minimum humidity and maximum humidity to calculate humidity at current time
     * if hour or time is greater than 12 noon we will calculate change in humidity in one hour multiplied with
     difference in hours and add minimum humidity to it
     * if time is less than 12 noon, we are substracting change in humidity in one hour from maximum humidity */
    
    private  int getHumidity(int hour, int minH, int maxH){
        if (hour > 12){
            return ((maxH-minH)/12)*(hour-12) + minH;
        }else {
            return maxH - ((maxH-minH)/12)*hour;
        }
    }
    
    
    // City Weather class to collect all data related to City and its weather.
    class CityWeather{
      
        private String cityName;
        private String position;
        private String date;
        private String temp;
        private String pressure;
        private String humidity;
        private String condition;
        
        /* Constructor of class CityWeather */
        public CityWeather(String cityName, String date, String position, float temp, float pressure, int humidity, String condition){
            this.cityName = cityName;
            this.date = date;
            this.position = position;
            this.temp = String.format("%.1f", temp);
            this.pressure = String.format("%.1f", pressure);
            this.humidity = String.valueOf(humidity);
            this.condition = condition;
            
        }
        
        @Override
        public String toString(){
            return cityName+"|"+position+"|"+date+"|"+temp+"|"+pressure+"hpa|"+humidity+"%|"+condition;
        }
    }
}
