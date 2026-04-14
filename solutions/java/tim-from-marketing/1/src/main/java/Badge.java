class Badge {
    public String print(Integer id, String name, String department) {
        String idPart = id != null ? "[" + id + "] - " : "";
        String deptPart = department != null ? department.toUpperCase() : "OWNER";
        return String.format("%s%s - %s", idPart, name, deptPart);
    }
}
