package com.channel.example.newsapp1;

    public class News {
        private String Title,SectionID,Date,Url,Author;

        public News(String ArticleTitle, String Section, String ArticleDate, String Articleurl,String ArticleAuthor){
            Title=ArticleTitle;
            SectionID=Section;
            Date=ArticleDate;
            Author =ArticleAuthor;
            Url=Articleurl;
        }
        public String getSectionID(){return SectionID;}
        public String getAuthor(){return Author;}
        public String  getDate (){return Date;}
        public String getTitle() {return  Title;}
        public String getUrl(){return Url;}
}
