package configs;

public class AnalysisConfig {

    /**
     * Determines if any analysis is done at all
     * OVERRIDES ALL OTHER ANALYSIS PARAMETERS
     */
    public static final boolean PERFORM_ANALYSIS = true;

    /**
     * Determines the width of the analysis charts
     */
    public static final int PLOT_WIDTH = 1920;

    /**
     * Determines the height of the analysis charts
     */
    public static final int PLOT_HEIGHT = 1080;

    /**
     * Determines if the charts will be exported to an svg file
     * export location is src/main/resources/charts/svg
     */
    public static boolean EXPORT_TO_SVG = true;

    /**
     * Determines wheter or not the performance requirements will be analyzed
     * and printed in the console
     */
    public static final boolean ANALYZE_BUSINESS_CONSTRAINTS = true;

    /**
     * Determines the minimum confidence of the business constraint analysis
     * SET TO 99% to ensure best confidence
     */
    public static final double MIN_BUSINESS_CONSTRAINT_CONFIDENCE = 0.99;

    /**
     * Plot the corporate and consumer arrival times
     */
    public static boolean PLOT_ARRIVALS = true;

    /**
     * Plot the corporate and consumer queue times
     */
    public static boolean PLOT_QUEUE_TIMES= true;

    /**
     * Plot the corporate and consumer service times
     */
    public static boolean PLOT_SERVICE_TIMES = true;
}
