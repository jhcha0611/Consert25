package dto;

public class ReviewDTO {
    private int reviewId;       // 후기 ID
    private int userId;         // 사용자 ID
    private int concertId;      // 콘서트 ID
    private int historyId;      // 예매 ID
    private String content;     // 후기 내용
    private int rating;         // 평점
    private String reviewDate;  // 작성 날짜
    private int like; 

    // 생성자
    public ReviewDTO(int reviewId, int userId, int concertId, int historyId, String content, int rating, String reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.concertId = concertId;
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

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
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
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
