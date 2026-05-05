package src.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class UITheme {

    private UITheme() {}

    public static final String FONT_FAMILY = "Inter";

    public static final Font FONT_EMPTY = Font.font(FONT_FAMILY, FontWeight.NORMAL, 12);
    public static final Font FONT_IDLE = Font.font(FONT_FAMILY, FontWeight.NORMAL, 10);
    public static final Font FONT_PROCESS = Font.font(FONT_FAMILY, FontWeight.BOLD, 13);
    public static final Font FONT_TIME = Font.font(FONT_FAMILY, FontWeight.NORMAL, 9);

    public static final Color COLOR_BACKGROUND = Color.web("#F8F9FA");
    public static final Color COLOR_TEXT_PRIMARY = Color.web("#2C3E50");
    public static final Color COLOR_TEXT_LIGHT = Color.web("#717D7E");
    public static final Color COLOR_IDLE_FILL = Color.web("#D5D8DC");
    public static final Color COLOR_IDLE_STROKE = Color.web("#AAB7B8");
    public static final Color COLOR_EMPTY = Color.GRAY;

    public static final Color[] PALETTE = {
        Color.web("#4A90D9"), Color.web("#E67E22"),
        Color.web("#2ECC71"), Color.web("#9B59B6"),
        Color.web("#E74C3C"), Color.web("#1ABC9C"),
        Color.web("#F39C12"), Color.web("#3498DB")
    };

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

    public static final String NO_DATA_TEXT = "No data";
    public static final String IDLE_TEXT = "IDLE";
    public static final String PROCESS_PREFIX = "P";
}
