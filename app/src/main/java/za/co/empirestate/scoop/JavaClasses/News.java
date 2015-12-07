package za.co.empirestate.scoop.JavaClasses;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by George_Kapoya on 15/11/18.
 */
public class News implements List<News> {


    public  String sources;
    public  String writer;
    public  String category;
    public  String sourceLink;
    public  String newsTitle;
    public  String newsContent;
    public  String likes;
    public  String times;
    public  String picUrl;

    public News() {
    }

    public News(String sources, String writer, String category, String sourceLink, String newsTitle, String newsContent, String likes, String times, String picUrl) {
        this.sources = sources;
        this.writer = writer;
        this.category = category;
        this.sourceLink = sourceLink;
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.likes = likes;
        this.times = times;
        this.picUrl = picUrl;
    }


    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public void add(int location, News object) {

    }

    @Override
    public boolean add(News object) {
        return false;
    }

    @Override
    public boolean addAll(int location, Collection<? extends News> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends News> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public News get(int location) {
        return null;
    }

    @Override
    public int indexOf(Object object) {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NonNull
    @Override
    public Iterator<News> iterator() {
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @Override
    public ListIterator<News> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<News> listIterator(int location) {
        return null;
    }

    @Override
    public News remove(int location) {
        return null;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public News set(int location, News object) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @NonNull
    @Override
    public List<News> subList(int start, int end) {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }
}
