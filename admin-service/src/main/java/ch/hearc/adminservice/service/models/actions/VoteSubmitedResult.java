package ch.hearc.adminservice.service.models.actions;

public class VoteSubmitedResult {

    private String message;

    private Boolean isSuccess;

    private VoteSubmitedResult(Boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public static VoteSubmitedResult ok(String message) {
        return new VoteSubmitedResult(Boolean.TRUE, message);
    }

    public static VoteSubmitedResult ko(String message) {
        return new VoteSubmitedResult(Boolean.FALSE, message);
    }
}
