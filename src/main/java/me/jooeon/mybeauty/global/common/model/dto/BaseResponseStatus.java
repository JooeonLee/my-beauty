package me.jooeon.mybeauty.global.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK,1000, "요청에 성공하였습니다."),

    // 1100 : 회원가입 마저 진행
//    SUCCESS_CONTINUE_SIGNUP(true, 1100, "회원가입을 마저 진행해주세요."),
//
//    /**
//     * 2000 : Request 오류
//     */
//    // 입력값 예외
//    INVALID_REQUEST(false, 2000, "잘못된 요청이 존재합니다."),
//
    // 2050 : 인증 예외
    // 권한 부족
    ACCESS_DENIED(false, HttpStatus.FORBIDDEN, 2051, "권한이 없는 유저의 접근입니다."),
    NO_PERMISSION_TO_ACCESS_RESOURCE(false, HttpStatus.FORBIDDEN, 2052, "해당 리소스에 대한 접근 권한이 없습니다."),

    // 인증 실패
    UNAUTHORIZED_ACCESS(false, HttpStatus.UNAUTHORIZED, 2053, "로그인이 필요합니다."),

    // 2100 : JWT 예외 - Filter에서 처리
    EMPTY_AUTHORIZATION_HEADER(false, HttpStatus.UNAUTHORIZED,1002, "Authorization 헤더가 존재하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(false, HttpStatus.UNAUTHORIZED, 2103, "이미 만료된 Access 토큰입니다."),
    UNSUPPORTED_TOKEN_TYPE(false, HttpStatus.UNAUTHORIZED,2104, "지원되지 않는 토큰 형식입니다."),
    MALFORMED_TOKEN_TYPE(false, HttpStatus.UNAUTHORIZED,2105, "인증 토큰이 올바르게 구성되지 않았습니다."),
    INVALID_SIGNATURE_JWT(false, HttpStatus.UNAUTHORIZED, 2106, "인증 시그니처가 올바르지 않습니다"),
    INVALID_TOKEN_TYPE(false, HttpStatus.UNAUTHORIZED, 2107, "잘못된 토큰입니다."),
    INVALID_ACCESS_TOKEN(false, HttpStatus.UNAUTHORIZED, 2108, "잘못된 access token 입니다."),
//
//    // 2200 : Refresh Token 예외 - Exception Handler에서 처리
//
//
//    // 2300 : MemberException
//
//    // 2400 : ReviewException
//
//    // 2500 : RequestParam exception
//    EMPTY_REQUEST_PARAMETER(false, 2098, "Request Parameter가 존재하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(false, HttpStatus.UNAUTHORIZED, 2099, "Request Parameter나 Path Variable의 유형이 불일치합니다."),
    HTTP_METHOD_TYPE_MISMATCH(false, HttpStatus.METHOD_NOT_ALLOWED, 2100, "지원되지 않는 Http Method입니다.");
//
//
//    // 2600 : RecordException
//    NONE_RECORD(false, 2500, "해당 기록이 존재하지 않습니다."),
//    ADD_RECORD_AMOUNT_ERROR(false, 2501, "기록 추가 중 데이터베이스에 오류가 발생하였습니다."),
//    INVALID_YEAR(false, 2502, "조회 연도가 유효하지 않습니다."),
//    INVALID_MONTH(false, 2503, "조회 월이 유효하지 않습니다."),
//
//    /**
//     * 3000 : Response 오류
//     */
//    // Common
//    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
//
//    /**
//     * 4000 : Database, Server 오류
//     */
//    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
//    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
//
//    /**
//     * 5000 : AWS 오류
//     */
//    // AWS S3
//    POST_IMAGE_CONVERT_ERROR(false, 5000, "사진이 없거나 변환되지 않았습니다."),
//    POST_IMAGE_INVALID_EXTENSION(false, 5001, "올바른 확장자가 아닙니다."),
//    IMAGE_CONVERT_ERROR(false, 5002, "사진이 없거나 변환되지 않았습니다."),
//    IMAGE_INVALID_EXTENSION(false, 5003, "올바른 확장자가 아닙니다."),
//
//    /**
//     * 6000 : 보안 이슈
//     */
//    // 보안(리프레시 토큰 탈취) 이슈
//    USING_STOLEN_TOKEN(false, 6000, "토큰이 탈취되었습니다.");

    private final boolean isSuccess;
    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int responseCode;
    private final String responseMessage;
}
