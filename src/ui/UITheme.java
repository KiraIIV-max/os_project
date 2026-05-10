package src.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class UITheme {

    private UITheme() {}

    // ── Typography ──────────────────────────────────────────────

    public static final String FONT_FAMILY = "Geist";

    public static final Font FONT_TITLE = Font.font(FONT_FAMILY, FontWeight.BOLD, 28);
    public static final Font FONT_SUBTITLE = Font.font(FONT_FAMILY, FontWeight.NORMAL, 14);
    public static final Font FONT_SECTION = Font.font(FONT_FAMILY, FontWeight.SEMI_BOLD, 16);
    public static final Font FONT_BODY = Font.font(FONT_FAMILY, FontWeight.NORMAL, 13);
    public static final Font FONT_SMALL = Font.font(FONT_FAMILY, FontWeight.NORMAL, 11);
    public static final Font FONT_METRIC_VALUE = Font.font(FONT_FAMILY, FontWeight.BOLD, 18);
    public static final Font FONT_METRIC_LABEL = Font.font(FONT_FAMILY, FontWeight.NORMAL, 12);
    public static final Font FONT_BADGE = Font.font(FONT_FAMILY, FontWeight.SEMI_BOLD, 10);

    // Gantt chart fonts (unchanged roles, size adjustments)
    public static final Font FONT_EMPTY = Font.font(FONT_FAMILY, FontWeight.NORMAL, 12);
    public static final Font FONT_IDLE = Font.font(FONT_FAMILY, FontWeight.NORMAL, 10);
    public static final Font FONT_PROCESS = Font.font(FONT_FAMILY, FontWeight.BOLD, 13);
    public static final Font FONT_TIME = Font.font(FONT_FAMILY, FontWeight.NORMAL, 9);

    // ── Colors ──────────────────────────────────────────────────

    public static final Color COLOR_BG = Color.web("#FBF8F5");
    public static final Color COLOR_SURFACE = Color.web("#F3F0ED");
    public static final Color COLOR_TEXT_PRIMARY = Color.web("#1E293B");
    public static final Color COLOR_TEXT_SECONDARY = Color.web("#64748B");
    public static final Color COLOR_BORDER = Color.web("#E2E0DD");
    public static final Color COLOR_ACCENT = Color.web("#0D9488");
    public static final Color COLOR_ACCENT_HOVER = Color.web("#0F766E");
    public static final Color COLOR_WINNER = Color.web("#059669");
    public static final Color COLOR_ERROR = Color.web("#DC2626");

    // Gantt-specific (keep old names for GanttChart.java compatibility)
    public static final Color COLOR_BACKGROUND = COLOR_BG;
    public static final Color COLOR_TEXT_LIGHT = COLOR_TEXT_SECONDARY;
    public static final Color COLOR_IDLE_FILL = Color.web("#E2E0DD");
    public static final Color COLOR_IDLE_STROKE = Color.web("#B8B6B2");
    public static final Color COLOR_EMPTY = COLOR_TEXT_SECONDARY;

    public static String css(Color color) {
        int r = (int) Math.round(color.getRed() * 255);
        int g = (int) Math.round(color.getGreen() * 255);
        int b = (int) Math.round(color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }

    // Process palette (unchanged)
    public static final Color[] PALETTE = {
        Color.web("#4A90D9"), Color.web("#E67E22"),
        Color.web("#2ECC71"), Color.web("#9B59B6"),
        Color.web("#E74C3C"), Color.web("#1ABC9C"),
        Color.web("#F39C12"), Color.web("#3498DB")
    };

    // ── Layout / Spacing ────────────────────────────────────────

    public static final int APP_WIDTH = 1000;
    public static final int APP_HEIGHT = 700;
    public static final int APP_MIN_WIDTH = 800;
    public static final int APP_MIN_HEIGHT = 600;

    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 16;
    public static final int SPACING_LG = 24;
    public static final int SPACING_XL = 32;

    public static final int RADIUS_SM = 4;
    public static final int RADIUS_MD = 8;
    public static final int RADIUS_LG = 12;
    public static final int RADIUS_PILL = 999;

    // Gantt chart layout (unchanged)
    public static final int UNIT_WIDTH = 40;
    public static final int PADDING = 80;
    public static final int BAR_Y = 24;
    public static final int BAR_HEIGHT = 48;
    public static final int CANVAS_HEIGHT = 120;
    public static final int START_X = 8;

    public static final int EMPTY_CANVAS_WIDTH = 400;
    public static final int EMPTY_CANVAS_HEIGHT = 64;
    public static final int EMPTY_TEXT_X = 8;
    public static final int EMPTY_TEXT_Y = 32;

    public static final double TEXT_CENTER_Y = BAR_Y + 24;
    public static final int TIME_LABEL_Y_OFFSET = 16;
    public static final double STROKE_WIDTH = 1.5;

    // ── Copy ────────────────────────────────────────────────────

    public static final String NO_DATA_TEXT = "No data";
    public static final String IDLE_TEXT = "IDLE";
    public static final String PROCESS_PREFIX = "P";
}
