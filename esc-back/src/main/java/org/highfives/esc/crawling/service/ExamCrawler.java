package org.highfives.esc.crawling.service;

import org.highfives.esc.crawling.entity.Exam;
import org.highfives.esc.crawling.repository.ExamRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ExamCrawler {

    private final ExamRepository examRepository;

    @Autowired
    public ExamCrawler(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Transactional
    public void CrawlingToeic() throws IOException {

        Document docs = Jsoup.connect("https://exam.toeic.co.kr/receipt/examSchList.php").get();
        Elements elements = docs.select("td[class='']");

        for (Element element : elements) {

            String examDate = element.text();
            examDate = examDate.substring(0, examDate.indexOf(' ')).replace('.', '-');

            Exam exam = new Exam();

            exam.setExamDate(examDate);
            exam.setStudyId(1);

            examRepository.save(exam);
        }
    }

    @Transactional
    public void CrawlingToeicSpeaking() throws IOException {
        Document docs = Jsoup.connect("https://www.toeicswt.co.kr/receipt/examSchList.php").get();
        Elements elements = docs.select("td[class='']");

        for (Element element : elements) {

            String examDate = element.text();
            examDate = examDate.substring(0, examDate.indexOf(' ')).replace('.', '-');

            Exam exam = new Exam();

            exam.setExamDate(examDate);
            exam.setStudyId(2);

            examRepository.save(exam);
        }
    }

    @Transactional
    public void CrawlingOpic() throws IOException {

        Document docs = Jsoup.connect("https://m.opic.or.kr/opics/servlet/controller.opic.site.receipt.AnnualScheduleServlet?p_process=select-list-mobile&p_tab=opic").get();
        Elements elements = docs.select("td");

        for (Element element : elements) {

            String examDate = element.text();

            if (examDate.length() == 14) {
                examDate = examDate.substring(0, 10).replace('.', '-');

                Exam exam = new Exam();

                exam.setExamDate(examDate);
                exam.setStudyId(3);

                examRepository.save(exam);
            }
        }
    }
}
