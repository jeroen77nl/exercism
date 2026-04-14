using System;
using System.ComponentModel;

static class AssemblyLine
{
    public static double SuccessRate(int speed)
    {
        if (speed < 0 || speed > 10)
        {
            throw new ArgumentException("speed must be in range 1 .. 10");
        }

        double rate = 0.0;
        if (speed == 0)
            rate = 0.0;
        else if (speed <= 4)
            rate = 1.0;
        else if (speed <= 8)
            rate = 0.9;
        else if (speed == 9)
            rate = 0.8;
        else rate = 0.77;

        return rate;
    }
    
    public static double ProductionRatePerHour(int speed)
    {
        return speed * SuccessRate(speed) * 221;
    }

    public static int WorkingItemsPerMinute(int speed)
    {
        return (int)(ProductionRatePerHour(speed) / 60);
    }
}
