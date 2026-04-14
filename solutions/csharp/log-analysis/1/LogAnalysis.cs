using System;

public static class LogAnalysis
{
    public static string SubstringAfter(this string logLine, string delimiter)
    {
        int pos = logLine.IndexOf(delimiter) + delimiter.Length;
        return logLine[pos..];
    }

    public static string SubstringBetween(this string logLine, string delimiterLeft, string delimiterRight)
    {
        int posLeft = logLine.IndexOf(delimiterLeft) + delimiterLeft.Length;
        int posRight = logLine.IndexOf(delimiterRight);
        return logLine[posLeft..posRight];
    }

    public static string Message(this string logLine)
    {
        return logLine.SubstringAfter("]: ");
    }

    public static string LogLevel(this string logLine)
    {
        return logLine.SubstringBetween("[", "]");
    }
}