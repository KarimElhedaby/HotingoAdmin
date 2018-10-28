package donation.solutions.hamza.com.hotingoadmin.model;

public class NotificationModel {
    private final boolean seen;

    private final String id;

    private final String targetUser;

    private final String subjectType;

    private final int v;

    private final String text;

    private final String creationDate;

    public NotificationModel(boolean seen, String id, String targetUser, String subjectType, int v,
                             String text, String creationDate) {
        this.seen = seen;
        this.id = id;
        this.targetUser = targetUser;
        this.subjectType = subjectType;
        this.v = v;
        this.text = text;
        this.creationDate = creationDate;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getId() {
        return id;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public int getV() {
        return v;
    }

    public String getText() {
        return text;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
