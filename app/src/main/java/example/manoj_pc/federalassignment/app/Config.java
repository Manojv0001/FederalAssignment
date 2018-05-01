package example.manoj_pc.federalassignment.app;

/**
 * Created by Ravi Tamada on 28/09/16.
 * www.androidhive.info
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static  final String API_KEY = "AAAArT7-2J0:APA91bGGDAkYV2LcES2Fh1YPNFD0yzr_2NEe-KR3VVNc3dUzMxQgAZXRFePBzllsbG6eKH10DfWuMsqjYHX_aD9T41btS3OWtb5LIxuBEuE2uULZbrRsbPTJvRnOjtVd03pF5OrLfiiu";
    public static final String EMPTY = "";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String MSGLIST = "MSGLIST";
    public static final String STATUS = "STATUS";
    public static final String CHANNEL_NAME = "manoj";
    public static final String CHANNEL_DESCRIPTION = "www.manoj.net";
    public static final String CHANNEL_ID = "my_channel_01";
}
