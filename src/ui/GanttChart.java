package src.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {
    private static final String FONT_FAMILY = "Geist";

    private static final Font FONT_EMPTY   = Font.font(FONT_FAMILY, FontWeight.NORMAL, 12);
    private static final Font FONT_IDLE    = Font.font(FONT_FAMILY, FontWeight.NORMAL, 12);
    private static final Font FONT_PROCESS = Font.font(FONT_FAMILY, FontWeight.BOLD, 12);
    private static final Font FONT_TIME    = Font.font(FONT_FAMILY, FontWeight.NORMAL, 8);

    private static final Color COLOR_BACKGROUND   = Color.web("#FAFBFD");
    private static final Color COLOR_TEXT_PRIMARY = Color.web("#1E293B");
    private static final Color COLOR_TEXT_LIGHT   = Color.web("#64748B");
    private static final Color COLOR_IDLE_FILL    = Color.web("#E2E0DD");
    private static final Color COLOR_IDLE_STROKE  = Color.web("#B8B6B2");

    private static final Color[] PALETTE = {
        Color.web("#4A90D9"), Color.web("#E67E22"),
        Color.web("#2ECC71"), Color.web("#9B59B6"),
        Color.web("#E74C3C"), Color.web("#1ABC9C"),
        Color.web("#F39C12"), Color.web("#3498DB")
    };

    private static final int UNIT_WIDTH       = 32;
    private static final int PADDING          = 16;
    private static final int BAR_Y            = 24;
    private static final int BAR_HEIGHT       = 48;
    private static final int CANVAS_HEIGHT    = 120;
    private static final int START_X          = 8;
    private static final int TIME_LABEL_Y_OFF = 16;
    private static final double TEXT_CENTER_Y = BAR_Y + 24;
    private static final double STROKE_WIDTH  = 1.5;

    public static Canvas createGanttCanvas(List<Integer> ganttLog) {
        if (ganttLog == null || ganttLog.isEmpty()) return createEmptyCanvas();

        List<int[]> blocks = mergeBlocks(ganttLog);

        int totalTime = ganttLog.size();
        int canvasW = totalTime * UNIT_WIDTH + PADDING;

        Canvas canvas = new Canvas(canvasW, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, canvasW, CANVAS_HEIGHT);

        int x = START_X;

        List<Integer> pidOrder = new ArrayList<>();
        for (int pid : ganttLog)
            if (pid != -1 && !pidOrder.contains(pid))
                pidOrder.add(pid);

        for (int[] block : blocks) {
            int start = block[0], end = block[1], pid = block[2];
            int w = (end - start) * UNIT_WIDTH;

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
        Canvas empty = new Canvas(400, 64);
        GraphicsContext gc = empty.getGraphicsContext2D();
        gc.setFill(COLOR_BACKGROUND);
        gc.setFont(FONT_EMPTY);
        gc.fillText("No data", 8, 32);
        return empty;
    }

    private static void drawIdleBlock(GraphicsContext gc, int x, int w) {
        gc.setFill(COLOR_IDLE_FILL);
        gc.fillRect(x, BAR_Y, w, BAR_HEIGHT);
        gc.setStroke(COLOR_IDLE_STROKE);
        gc.strokeRect(x, BAR_Y, w, BAR_HEIGHT);

        gc.setFill(COLOR_TEXT_LIGHT);
        gc.setFont(FONT_IDLE);
        String label = "IDLE";
        double labelX = x + (w - measureTextWidth(FONT_IDLE, label)) / 2.0;
        gc.fillText(label, labelX, TEXT_CENTER_Y);
    }

    private static void drawProcessBlock(GraphicsContext gc, int x, int w, int pid, List<Integer> pidOrder) {
        int colIdx = pidOrder.indexOf(pid) % PALETTE.length;
        Color c = PALETTE[colIdx];

        gc.setFill(c);
        gc.fillRect(x, BAR_Y, w, BAR_HEIGHT);
        gc.setStroke(c.darker());
        gc.setLineWidth(STROKE_WIDTH);
        gc.strokeRect(x, BAR_Y, w, BAR_HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(FONT_PROCESS);
        String label = "P" + pid;
        double labelX = x + (w - measureTextWidth(FONT_PROCESS, label)) / 2.0;
        gc.fillText(label, labelX, TEXT_CENTER_Y);
    }

    private static void drawTimeLabel(GraphicsContext gc, int x, int time) {
        gc.setFill(COLOR_TEXT_PRIMARY);
        gc.setFont(FONT_TIME);
        gc.fillText(String.valueOf(time), x, BAR_Y + BAR_HEIGHT + TIME_LABEL_Y_OFF);
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
