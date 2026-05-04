import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {

    private static final Color[] PALETTE = {
        Color.web("#4A90D9"), Color.web("#E67E22"),
        Color.web("#2ECC71"), Color.web("#9B59B6"),
        Color.web("#E74C3C"), Color.web("#1ABC9C"),
        Color.web("#F39C12"), Color.web("#3498DB")
    };

    public static Canvas createGanttCanvas(List<Integer> ganttLog) {
        if (ganttLog == null || ganttLog.isEmpty()) {
            Canvas empty = new Canvas(400, 60);
            GraphicsContext gc = empty.getGraphicsContext2D();
            gc.setFill(Color.GRAY);
            gc.setFont(Font.font("Arial", 14));
            gc.fillText("No data", 10, 30);
            return empty;
        }

        List<int[]> blocks = mergeBlocks(ganttLog);

        int unitW     = 40;
        int totalTime = ganttLog.size();
        int canvasW   = totalTime * unitW + 80;
        int canvasH   = 120;

        Canvas canvas = new Canvas(canvasW, canvasH);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.web("#F8F9FA"));
        gc.fillRect(0, 0, canvasW, canvasH);

        int barY = 20;
        int barH = 50;
        int x    = 10;

        List<Integer> pidOrder = new ArrayList<>();
        for (int pid : ganttLog)
            if (pid != -1 && !pidOrder.contains(pid))
                pidOrder.add(pid);

        for (int[] block : blocks) {
            int start = block[0], end = block[1], pid = block[2];
            int w = (end - start) * unitW;

            if (pid == -1) {
                gc.setFill(Color.web("#D5D8DC"));
                gc.fillRect(x, barY, w, barH);
                gc.setStroke(Color.web("#AAB7B8"));
                gc.strokeRect(x, barY, w, barH);
                gc.setFill(Color.web("#717D7E"));
                gc.setFont(Font.font("Arial", 11));
                gc.fillText("IDLE", x + w / 2.0 - 14, barY + 29);
            } else {
                int colIdx = pidOrder.indexOf(pid) % PALETTE.length;
                Color c = PALETTE[colIdx];

                gc.setFill(c);
                gc.fillRect(x, barY, w, barH);
                gc.setStroke(c.darker());
                gc.setLineWidth(1.5);
                gc.strokeRect(x, barY, w, barH);

                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                gc.fillText("P" + pid, x + w / 2.0 - 10, barY + 29);
            }

            gc.setFill(Color.web("#2C3E50"));
            gc.setFont(Font.font("Arial", 10));
            gc.fillText(String.valueOf(start), x, barY + barH + 16);

            x += w;
        }

        gc.setFill(Color.web("#2C3E50"));
        gc.setFont(Font.font("Arial", 10));
        gc.fillText(String.valueOf(totalTime), x, barY + barH + 16);

        return canvas;
    }

    private static List<int[]> mergeBlocks(List<Integer> ganttLog) {
        List<int[]> blocks     = new ArrayList<>();
        int         start      = 0;
        int         currentPid = ganttLog.get(0);

        for (int t = 1; t < ganttLog.size(); t++) {
            if (ganttLog.get(t) != currentPid) {
                blocks.add(new int[]{start, t, currentPid});
                start      = t;
                currentPid = ganttLog.get(t);
            }
        }
        blocks.add(new int[]{start, ganttLog.size(), currentPid});
        return blocks;
    }
}