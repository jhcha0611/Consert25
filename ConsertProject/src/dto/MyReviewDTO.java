package dto;

public class MyReviewDTO {
    private int reviewId;       // 후기 ID
    private int userId;         // 사용자 ID
    private int consertId;      // 콘서트 ID
    private int historyId;      // 예매 ID
    private String content;     // 후기 내용
    private int rating;         // 평점
    private String reviewDate;  // 작성 날짜

    // 생성자
    public MyReviewDTO(int reviewId, int userId, int consertId, int historyId, String content, int rating, String reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.consertId = consertId;
        this.historyId = historyId;
        this.content = content;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    // Getter & Setter
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getConsertId() {
        return consertId;
    }

    public void setConsertId(int consertId) {
        this.consertId = consertId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
