package com.madeyepeople.pocketpt.domain.ptMatching.mapper;

import com.madeyepeople.pocketpt.domain.ptMatching.dto.PtMatchingSummary;
import com.madeyepeople.pocketpt.domain.ptMatching.entity.PtMatching;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ToPtMatchingListResponse {

    private final ToPtMatchingSummary toPtMatchingSummary;
    public List<PtMatchingSummary> fromPtMatchingEntityList(List<PtMatching> ptMatchingList, Long requesterAccountId) {
        List<PtMatchingSummary> ptMatchingSummaryList = ptMatchingList.stream()
                .map(ptMatching -> toPtMatchingSummary.fromPtMatchingEntity(ptMatching, requesterAccountId))
                .collect(Collectors.toList());

        return ptMatchingSummaryList;
    }

    public List<PtMatchingSummary> addChattingRoomId(List<PtMatchingSummary> ptMatchingSummaryList, HashMap<Long, Long> chattingRoomIdMap) {
        for (PtMatchingSummary ptMatchingSummary : ptMatchingSummaryList) {
            ptMatchingSummary.setChattingRoomId(chattingRoomIdMap.get(ptMatchingSummary.getAccountId()));
        }
        return ptMatchingSummaryList;
    }
}
