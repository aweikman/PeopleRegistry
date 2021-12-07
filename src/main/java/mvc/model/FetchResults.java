package mvc.model;

import java.util.ArrayList;

public class FetchResults {

    private ArrayList<Person> people;
    private int firstIndex;
    private int lastIndex;
    private int offSet;
    private int pageSize;
    private int totalElements;
    private int totalPages;

    public FetchResults() {
    }


    public FetchResults(int offSet, int totalElements, int pageSize, ArrayList<Person> people) {

        this.offSet = offSet;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.people = people;
    }

    //accessors

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int firstIndex) {
        this.firstIndex = firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}


