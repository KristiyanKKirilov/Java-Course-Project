package bg.tu_varna.sit.a2.f23621649.models;

import java.util.List;

public class Pagination {
    private final int DEFAULT_ROWS = 5;
    private final List<String> content;
    private final int recordsPerPage;
    private int currentPage;

    public Pagination(List<String> content, int recordsPerPage) {
        this.content = content;
        this.recordsPerPage = recordsPerPage;
        this.currentPage = 0;
    }

    public void showCurrentPage() {
        int start = (currentPage * recordsPerPage) + DEFAULT_ROWS;
        int end = Math.min(start + recordsPerPage, content.size());

        for (int i = 0; i < DEFAULT_ROWS; i++) {
            ConsoleWriter.printLine(content.get(i));
        }


        for (int i = start; i < end; i++) {
            ConsoleWriter.printLine(content.get(i));
        }

        String message = String.format("Page %d of %d%n", currentPage + 1, getTotalPages());
        ConsoleWriter.printLine(message);
    }

    public boolean hasNext() {
        return currentPage < getTotalPages() - 1;
    }

    public boolean hasPrevious() {
        return currentPage > 0;
    }

    public void nextPage() {
        if (hasNext()) currentPage++;
    }

    public void previousPage() {
        if (hasPrevious()) currentPage--;
    }

    public int getTotalPages() {
        int dataRowCount = content.size() - DEFAULT_ROWS;

        if (dataRowCount > 0 && content.get(content.size() - 1).trim().startsWith("+")) {

            dataRowCount -= 1;
        }

        return (int) Math.ceil((double) dataRowCount / recordsPerPage);
    }
}

