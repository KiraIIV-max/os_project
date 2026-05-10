package src.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {

    public static Canvas createGanttCanvas(List<Integer> ganttLog) {
        if (ganttLog == null || ganttLog.isEmpty()) return createEmptyCanvas();

        List<int[]> blocks = mergeBlocks(ganttLog);

        int totalTime = ganttLog.size();
        int canvasW = totalTime * UITheme.UNIT_WIDTH + UITheme.PADDING;

        Canvas canvas = new Canvas(canvasW, UITheme.CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(UITheme.COLOR_BACKGROUND);
        gc.fillRect(0, 0, canvasW, UITheme.CANVAS_HEIGHT);

        int x = UITheme.START_X;

        List<Integer> pidOrder = new ArrayList<>();
        for (int pid : ganttLog)
            if (pid != -1 && !pidOrder.contains(pid))
                pidOrder.add(pid);

        for (int[] block : blocks) {
            int start = block[0], end = block[1], pid = block[2];
            int w = (end - start) * UITheme.UNIT_WIDTH;

            if (pid == -1) {
                drawIdleBlock(gc, x, w);
            } else {
                drawProcessBlock(gc, x, w, pid, pidOrder);
            }

            drawTimeLabel(gc, x, start);

            x += w;
        }

        drawTimeLabel(gc, x, totalTime);

        return canvas;
    }

    private static Canvas createEmptyCanvas() {
        Canvas empty = new Canvas(UITheme.EMPTY_CANVAS_WIDTH, UITheme.EMPTY_CANVAS_HEIGHT);
        GraphicsContext gc = empty.getGraphicsContext2D();
        gc.setFill(UITheme.COLOR_EMPTY);
        gc.setFont(UITheme.FONT_EMPTY);
        gc.fillText(UITheme.NO_DATA_TEXT, UITheme.EMPTY_TEXT_X, UITheme.EMPTY_TEXT_Y);
        return empty;
    }

    private static void drawIdleBlock(GraphicsContext gc, int x, int w) {
        gc.setFill(UITheme.COLOR_IDLE_FILL);
        gc.fillRect(x, UITheme.BAR_Y, w, UITheme.BAR_HEIGHT);
        gc.setStroke(UITheme.COLOR_IDLE_STROKE);
        gc.strokeRect(x, UITheme.BAR_Y, w, UITheme.BAR_HEIGHT);

        gc.setFill(UITheme.COLOR_TEXT_LIGHT);
        gc.setFont(UITheme.FONT_IDLE);
        String label = UITheme.IDLE_TEXT;
        double labelX = x + (w - measureTextWidth(UITheme.FONT_IDLE, label)) / 2.0;
        gc.fillText(label, labelX, UITheme.TEXT_CENTER_Y);
    }

    private static void drawProcessBlock(GraphicsContext gc, int x, int w, int pid, List<Integer> pidOrder) {
        int colIdx = pidOrder.indexOf(pid) % UITheme.PALETTE.length;
        Color c = UITheme.PALETTE[colIdx];

        gc.setFill(c);
        gc.fillRect(x, UITheme.BAR_Y, w, UITheme.BAR_HEIGHT);
        gc.setStroke(c.darker());
        gc.setLineWidth(UITheme.STROKE_WIDTH);
        gc.strokeRect(x, UITheme.BAR_Y, w, UITheme.BAR_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(UITheme.FONT_PROCESS);
        String label = UITheme.PROCESS_PREFIX + pid;
        double labelX = x + (w - measureTextWidth(UITheme.FONT_PROCESS, label)) / 2.0;
        gc.fillText(label, labelX, UITheme.TEXT_CENTER_Y);
    }

    private static void drawTimeLabel(GraphicsContext gc, int x, int time) {
        gc.setFill(UITheme.COLOR_TEXT_PRIMARY);
        gc.setFont(UITheme.FONT_TIME);
        gc.fillText(String.valueOf(time), x, UITheme.BAR_Y + UITheme.BAR_HEIGHT + UITheme.TIME_LABEL_Y_OFFSET);
    }

    private static double measureTextWidth(Font font, String text) {
        Text helper = new Text(text);
        helper.setFont(font);
        return helper.getLayoutBounds().getWidth();
    }

    private static List<int[]> mergeBlocks(List<Integer> ganttLog) {
        List<int[]> blocks = new ArrayList<>();
        int start = 0;
        int currentPid = ganttLog.get(0);

        for (int t = 1; t < ganttLog.size(); t++) {
            if (ganttLog.get(t) != currentPid) {
                blocks.add(new int[]{start, t, currentPid});
                start = t;
                currentPid = ganttLog.get(t);
            }
        }
        blocks.add(new int[]{start, ganttLog.size(), currentPid});
        return blocks;
    }
}
