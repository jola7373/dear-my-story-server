package com.qnnect.cafe.api;

import com.qnnect.cafe.service.CafeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;

public class CafeControllerTest {
    @MockBean
    private CafeServiceImpl cafeService;

//    @DisplayName("로그인한 사용자가 올바른 코드로 그룹에 참여한다.")
//    @Test
//    void joinCafeWithLoginUser() throws Exception {
//        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
//                .memberId(PERSIST_MEMBER.getId())
//                .content("new post")
//                .location(JAMSIL_STATION)
//                .authorAddress(AUTHOR_ADDRESS)
//                .build();
//
//        PostCreateResponse postCreateResponse = new PostCreateResponse(1L);
//
//        String request = objectMapper.writeValueAsString(postCreateRequest);
//        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
//        when(jwtTokenProvider.getSubject(anyString())).thenReturn("id");
//        when(memberRepository.findByoAuthId(anyString())).thenReturn(PERSIST_MEMBER);
//        when(postService.createPost(any(), any())).thenReturn(postCreateResponse);
//
//        this.mockMvc.perform(post("/posts")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(request))
//                .andExpect(status().isCreated())
//                .andDo(print());
//
//        verify(postService).createPost(any(), any());
//    }
}
