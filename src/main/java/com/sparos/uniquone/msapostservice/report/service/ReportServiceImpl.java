package com.sparos.uniquone.msapostservice.report.service;

import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.qna.domain.QnA;
import com.sparos.uniquone.msapostservice.qna.domain.QnAUtils;
import com.sparos.uniquone.msapostservice.report.domain.Report;
import com.sparos.uniquone.msapostservice.report.domain.ReportUtils;
import com.sparos.uniquone.msapostservice.report.dto.ReportInputDto;
import com.sparos.uniquone.msapostservice.report.repository.IReportRepository;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService{

    private final IReportRepository iReportRepository;
    private final IPostRepository iPostRepository;

    // 신고 등록
    @Override
    public JSONObject createReport(ReportInputDto reportInputDto, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        Report report = iReportRepository.save(ReportUtils.reportInputDtoToEntity(
                reportInputDto,
                iPostRepository.findById(reportInputDto.getPostId())
                        .orElseThrow(()-> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.NO_CONTENT)),
                request));
        jsonObject.put("data", report);

        return jsonObject;
    }

    @Override
    public JSONObject findAllReport() {

        JSONObject jsonObject = new JSONObject();
        List<Report> reports = iReportRepository.findAll();

        if (reports.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION, HttpStatus.NO_CONTENT);

        jsonObject.put("data", reports.stream().map(report ->
                ReportUtils.entityToReportOutDto(report))
        );

        return jsonObject;
    }
}
