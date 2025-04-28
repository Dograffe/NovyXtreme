package novyXtreme.utils;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
public class teleportUtils {


    // Get the speed at which the player/entity will exit the destination portal
    public static Vector getExitPortalVector(BlockFace facingDirection){
        double exitSpeed = 0.2;
        switch (facingDirection) {
            case NORTH:
                return new Vector(0, 0, -exitSpeed);
            case SOUTH:
                return new Vector(0, 0, 1);
            case EAST:
                return new Vector(exitSpeed, 0, 0);
            case WEST:
                return new Vector(-exitSpeed, 0, 0);
            case UP:
                return new Vector(0, exitSpeed, 0);
            case DOWN:
                return new Vector(0, -exitSpeed, 0);
        }
        return new Vector(0, 0, 0);
    }
}
