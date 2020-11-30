package model.DB;

public class CommentDTO {
    private String _comment;
    private String _password;

    public String getComment() {
        return _comment;
    }

    public String getPassword() {
        return _password;
    }

    public void setComment(String comment) {
        this._comment = comment;
    }

    public void setPassword(String password) {
        this._password = password;
    }
}
