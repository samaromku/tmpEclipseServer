package network;

public class TaskEnum {
    public static final String TAKE_INFO = "к сведению";
    public static final String INSPECTION = "приемка";
    public static final String UUITE = "УУТЭ";
    public static final String ITP = "ИТП";
    public static final String ARTF = "АРТФ";
    public static final String APARTMENT = "Квартирная";

    public static final String NEW_TASK = "новое задание";
    public static final String DISTRIBUTED_TASK = "распределено";
    public static final String DOING_TASK = "выполняется";
    public static final String DONE_TASK = "выполнено";
    public static final String DISAGREE_TASK = "отказ";
    public static final String CONTROL_TASK = "контроль";
    public static final String NEED_HELP = "нужна помощь";

    public static final String STANDART = "Стандартная";
    public static final String AVARY = "Аварийная";
    public static final String TIME = "Периодическая";
    public static final String INFO = "Информационная";
	
	private static String[] importances = new String[]{STANDART, AVARY, INFO, TIME};
    private static String[] statuses = new String[]{NEW_TASK, DISTRIBUTED_TASK, DOING_TASK, DONE_TASK, DISAGREE_TASK, CONTROL_TASK, NEED_HELP};    
    private static String[] types = new String[]{TAKE_INFO, INSPECTION, UUITE, ITP, ARTF, APARTMENT};
    
    

    public static String[] getStatuses(){
    	return statuses;
    }
    
    public static String[] getTypes(){
    	return types;
    }
    
    public static String[] getImportances(){
    	return importances;
    }

}
