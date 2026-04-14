using System;
using System.Security.Cryptography.X509Certificates;

static class Badge
{
    public static string Print(int? id, string name, string? department)
    {
        string dep = department?.ToUpper() ?? "OWNER";
        string badge;

        if (id == null)
        {
            badge = $"{name} - {dep}";
        }
        else
        {
            badge = $"[{id}] - {name} - {dep}";
        }
        return badge;
    }
}
