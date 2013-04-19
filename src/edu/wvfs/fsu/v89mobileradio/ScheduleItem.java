package edu.wvfs.fsu.v89mobileradio;



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
	
	public boolean IsPlaying(Time t)
	{
		boolean rightDay = Day <= t.weekDay && DayEnd >= t.weekDay;
		boolean rightHour = Hour <= t.hour && HourEnd >= t.hour;
		boolean rightMinute = Minute <= t.minute && MinuteEnd >= t.minute;
		boolean flag = true;
		if(Flag != FlagType.None)
			switch(Flag)
			{
			case FirstWednesday:
				flag = t.weekDay == Time.WEDNESDAY && Math.ceil(t.monthDay/7) == 1;
				break;
			case ThirdWednesday:
				flag = t.weekDay == Time.WEDNESDAY && Math.ceil(t.monthDay/7) == 3;
				break;
			case FourthWednesday:
				flag = t.weekDay == Time.WEDNESDAY && Math.ceil(t.monthDay/7) == 4;
				break;
			case FifthWednesday:
				flag = t.weekDay == Time.WEDNESDAY && Math.ceil(t.monthDay/7) == 5;
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
