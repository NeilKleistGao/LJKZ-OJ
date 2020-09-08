package utils;

public class PermissionChecker {
    public final static int ADMIN_PERMISSION = 1;
    public final static int PROBLEM_PERMISSION = 2;
    public final static int COMPETITION_PERMISSION = 4;

    private PermissionChecker() {
    }

    private static PermissionChecker checker = null;

    public static PermissionChecker getInstance() {
        if (checker == null) {
            checker = new PermissionChecker();
        }

        return checker;
    }

    public boolean check(int permissions, int pcode) {
        return (permissions & pcode) != 0;
    }
}
