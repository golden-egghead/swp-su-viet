package com.example.SuViet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.text.ParseException;


import com.example.SuViet.model.Article;
import com.example.SuViet.model.Comment;
import com.example.SuViet.model.Period;
import com.example.SuViet.model.RepliesComment;
import com.example.SuViet.model.Vote;

@Getter
@Setter
@NoArgsConstructor
public class ArticleListDTO {
    private int articleID;
    private String title;
    private String context;
    private String photo;
    private String createdDate;
    private boolean status;
    private int articleView;
    private String fullName;
    private int voteLevel;
    private String periodName;
    private Collection<Comment> comments;
    private Collection<RepliesComment> repliesComments;

    public ArticleListDTO(int articleID, String title, String context, String photo, String createdDate,
                          boolean status, int articleView, String fullName, int voteLevel, String periodName,
                          Collection<Comment> comments, Collection<RepliesComment> repliesComments) {
        this.articleID = articleID;
        this.title = title;
        this.context = context;
        this.photo = photo;
        this.createdDate = createdDate;
        this.status = status;
        this.articleView = articleView;
        this.fullName = fullName;
        this.voteLevel = voteLevel;
        this.periodName = periodName;
        this.comments = comments;
        this.repliesComments = repliesComments;
    }

    public static ArticleListDTO convertToDTO(Article article) {
        ArticleListDTO dto = new ArticleListDTO();
        dto.setArticleID(article.getArticleID());
        dto.setTitle(article.getTitle());
        dto.setContext(article.getContext());
        dto.setPhoto(article.getPhoto());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dto.setCreatedDate(dateFormat.format(article.getCreatedDate()));

        dto.setStatus(article.isStatus());
        dto.setArticleView(article.getArticleView());
        dto.setFullName(article.getUser().getFullname());
        dto.setVoteLevel(getAverageVoteLevel(article.getVotes()));
        dto.setPeriodName(getPeriodNames(article.getPeriods()));
        dto.setComments(getCommentList(article.getComments()));
        dto.setRepliesComments(getRepliesComments(article.getComments()));
        return dto;
    }


    public Article convertToEntity() {
        Article article = new Article();
        article.setArticleID(this.articleID);
        article.setTitle(this.title);
        article.setPhoto(this.photo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date formattedDate = dateFormat.parse(this.createdDate);
            article.setCreatedDate(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        article.setArticleView(this.articleView);
        return article;
    }

    private static int getAverageVoteLevel(Collection<Vote> votes) {
        if (votes.isEmpty()) {
            return 0;
        }

        int totalVotes = votes.size();
        int totalVoteLevel = 0;
        for (Vote vote : votes) {
            totalVoteLevel += vote.getVoteLevel();
        }
        return totalVoteLevel / totalVotes;
    }

    private static String getPeriodNames(Collection<Period> periods) {
        if (periods.isEmpty()) {
            return "";
        }

        return periods.stream()
                .map(Period::getPeriodName)
                .collect(Collectors.joining(", "));
    }
}
