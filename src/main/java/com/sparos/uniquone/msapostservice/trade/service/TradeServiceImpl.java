package com.sparos.uniquone.msapostservice.trade.service;

import com.sparos.uniquone.msapostservice.corn.domain.Corn;
import com.sparos.uniquone.msapostservice.corn.repository.ICornRepository;
import com.sparos.uniquone.msapostservice.post.domain.Post;
import com.sparos.uniquone.msapostservice.post.domain.PostType;
import com.sparos.uniquone.msapostservice.post.repository.IPostImgRepository;
import com.sparos.uniquone.msapostservice.post.repository.IPostRepository;
import com.sparos.uniquone.msapostservice.review.utils.ReviewUtils;
import com.sparos.uniquone.msapostservice.trade.domain.Trade;
import com.sparos.uniquone.msapostservice.trade.dto.TradeInputDto;
import com.sparos.uniquone.msapostservice.trade.repository.ITradeRepository;
import com.sparos.uniquone.msapostservice.trade.utils.TradeUtils;
import com.sparos.uniquone.msapostservice.util.jwt.JwtProvider;
import com.sparos.uniquone.msapostservice.util.response.ExceptionCode;
import com.sparos.uniquone.msapostservice.util.response.UniquOneServiceException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TradeServiceImpl implements ITradeService{

    private final ITradeRepository iTradeRepository;
    private final IPostRepository iPostRepository;
    private final IPostImgRepository iPostImgRepository;
    private final ICornRepository iCornRepository;

    // 거래 등록
    @Override
    public JSONObject createTrade(TradeInputDto tradeInputDto, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        Post post = iPostRepository.findById(tradeInputDto.getPostId())
                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION));

        // 판매 중단 or 판매 완료
        if (post.getPostType().equals(PostType.DISCONTINUED) || post.getPostType().equals(PostType.SOLD_OUT))
            throw new UniquOneServiceException(ExceptionCode.POST_TYPE_NOT_TRADE);

        Trade trade = iTradeRepository.save(
                Trade.builder()
                        .sellerId(JwtProvider.getUserPkId(request))
                        .buyerId(tradeInputDto.getBuyerId())
                        .post(post)
                        .build());

        jsonObject.put("data", trade); // todo 필요없으면 변경

        return jsonObject;
    }

    // 판매 내역 조회
    @Override
    public JSONObject findSell(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        List<Trade> trades = iTradeRepository.findBySellerId(JwtProvider.getUserPkId(request));

        if (trades.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION);

        jsonObject.put("data", trades.stream().map(trade ->
                TradeUtils.entityToTradeOutDto(
                        trade,
                        iPostRepository.findById(trade.getPost().getId())
                                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION)),
                        iPostImgRepository.findUrlByPostId(trade.getPost().getId())))
        );

        return jsonObject;
    }


    // 판매 내역 조회
    @Override
    public JSONObject findBuy(HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        List<Trade> trades = iTradeRepository.findByBuyerId(JwtProvider.getUserPkId(request));

        if (trades.isEmpty())
            throw new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION);

        jsonObject.put("data", trades.stream().map(trade ->
                TradeUtils.entityToTradeOutDto(
                        trade,
                        iPostRepository.findById(trade.getPost().getId())
                                .orElseThrow(() -> new UniquOneServiceException(ExceptionCode.NO_SUCH_ELEMENT_EXCEPTION)),
                        iPostImgRepository.findUrlByPostId(trade.getPost().getId())))
        );

        return jsonObject;
    }
}
