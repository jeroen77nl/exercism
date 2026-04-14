using System;
using System.Globalization;

static class LogLine
{
    public static string Message(string logLine)
    {
        var arr = logLine.Split(":");
        return arr[1].Trim();
    }

    public static string LogLevel(string logLine)
    {
        var parts = logLine.Split(":");
        var levelPart = parts[0];
        return RemoveBrackets(levelPart).ToLower();
    }

    public static string Reformat(string logLine)
    {
        return $"{Message(logLine)} ({LogLevel(logLine)})";
    }

    private static string RemoveBrackets(string s)
    {
        return s.Substring(1, s.Length - 2);
    }
}
