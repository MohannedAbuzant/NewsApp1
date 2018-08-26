package com.channel.example.newsapp1;

    public class News {
        private String Title,Author,Date,Url;

        public News(String ArticleTitle, String AuthorName, String ArticleDate, String Articleurl){
            Title=ArticleTitle;
            Author=AuthorName;
            Date=ArticleDate;
            Url=Articleurl;
        }
        public String getAuthor(){return Author;}
        public String  getDate (){return Date;}
        public String getTitle() {return  Title;}
        public String getUrl(){return Url;}
}
