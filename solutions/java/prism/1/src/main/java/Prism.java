import java.util.ArrayList;
import java.util.List;

public class Prism {

    public record LaserInfo(double x, double y, double angle) {
    }

    public record PrismInfo(int id, double x, double y, double angle) {
    }

    public static List<Integer> findSequence(LaserInfo laser, List<PrismInfo> prisms) {
        List<Integer> result = new ArrayList<>();

        while (true) {
            PrismInfo best = null;
            double bestDist = Double.POSITIVE_INFINITY;

            for (PrismInfo prism : prisms) {
                if (!align(laser, prism)) continue;

                double dx = prism.x() - laser.x();
                double dy = prism.y() - laser.y();
                double dist = dx * dx + dy * dy; // squared distance

                if (dist < bestDist) {
                    bestDist = dist;
                    best = prism;
                }
            }

            if (best == null) break;

            result.add(best.id());
            laser = new LaserInfo(best.x(), best.y(), laser.angle() + best.angle());
        }

        return result;
    }

    private static boolean align(LaserInfo laser, PrismInfo prism) {
        // robuustere point check
        if (samePoint(laser.x(), laser.y(), prism.x(), prism.y()))
            return false;

        double rad = Math.toRadians(laser.angle());
        double vx = Math.cos(rad);
        double vy = Math.sin(rad);

        double dx = prism.x() - laser.x();
        double dy = prism.y() - laser.y();

        // determinant (collineariteit)
        double diff = dx * vy - dy * vx;
        if (Math.abs(diff) > 0.01) return false;

        // dot product (richting)
        double dot = dx * vx + dy * vy;

        return dot >= 0;
    }

    private static boolean samePoint(double x1, double y1, double x2, double y2) {
        double eps = 1e-9;
        return Math.abs(x1 - x2) < eps && Math.abs(y1 - y2) < eps;
    }
}