package edu.wvfs.fsu.v89mobileradio;



import java.util.Calendar;
import java.util.TimeZone;

import android.text.format.Time;

public class ScheduleItem implements Comparable<ScheduleItem>{
	public static enum FlagType {
		FirstWednesday,
		ThirdWednesday,
		FourthWednesday,
		FifthWednesday,
		None
	}
	
	public int Day;
	public int DayEnd;
	public int End;
	public int Hour;
	public int Minute;
	public int HourEnd;
	public int MinuteEnd;
	public FlagType Flag;
	public String Title;
	public String Description;
	
	public ScheduleItem(String title, String desc, int d,int de, int h, int he, int m, int me, FlagType flag)
	{
		Title = title;
		Description = desc;
		Day = d;
		DayEnd = de;
		Hour = h;
		HourEnd = he;
		Minute = m;
		MinuteEnd = me;
		Flag = flag;
	}
	
	public boolean IsPlaying()
	{
	  TimeZone tz = TimeZone.getTimeZone("America/New_York");
	  Calendar t = Calendar.getInstance(tz);
	  int CurrDay = t.get(Calendar.DAY_OF_WEEK)-1;
	  int CurrMonthDay = t.get(Calendar.DAY_OF_MONTH)-1;
	  int CurrHour = t.get(Calendar.HOUR_OF_DAY);
	  int CurrMinute = t.get(Calendar.MINUTE);
	  
		boolean rightDay = Day <= CurrDay && DayEnd >= CurrDay;
		boolean rightHour = Hour <= CurrHour && HourEnd >= CurrHour;
		boolean rightMinute = Minute <= CurrMinute && MinuteEnd >= CurrMinute;
		boolean flag = true;
		if(Flag != FlagType.None)
			switch(Flag)
			{
			case FirstWednesday:
                flag = CurrDay == Time.WEDNESDAY && Math.ceil(CurrMonthDay/7) == 1;
                break;
            case ThirdWednesday:
                flag = CurrDay == Time.WEDNESDAY && Math.ceil(CurrMonthDay/7) == 3;
                break;
            case FourthWednesday:
                flag = CurrDay == Time.WEDNESDAY && Math.ceil(CurrMonthDay/7) == 4;
                break;
            case FifthWednesday:
                flag = CurrDay == Time.WEDNESDAY && Math.ceil(CurrMonthDay/7) == 5;
                break;
            default:
                break;
            }
		return rightDay && rightHour && rightMinute && flag;
			
	}
	
	public int CalculateTimestamp()
	{
		return Day*10000 + Hour*100+ Minute;
	}

	@Override
	public int compareTo(ScheduleItem another) {
		
		if(CalculateTimestamp() < another.CalculateTimestamp())
			return -1;
		if(CalculateTimestamp() > another.CalculateTimestamp())
			return 1;
		return 0;
	}
}
