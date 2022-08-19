package com.oceandiary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceandiary.api.config.WebMvcConfig;
import com.oceandiary.api.user.security.token.JwtAuthEntryPoint;
import com.oceandiary.api.user.security.token.JwtProps;
import com.oceandiary.api.user.security.token.TokenProvider;
import com.oceandiary.api.user.security.userdetails.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@Import({
        WebMvcConfig.class,
        TokenProvider.class,
        JwtProps.class,
        RestDocsConfig.class,
})
@WithMockCustomUser
public abstract class MvcTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected CustomUserDetailService customUserDetailService;
    @MockBean
    protected JwtAuthEntryPoint jwtAuthEntryPoint;
}
