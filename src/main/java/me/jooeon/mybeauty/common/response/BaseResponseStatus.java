package me.jooeon.mybeauty.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // 1100 : 회원가입 마저 진행
    SUCCESS_CONTINUE_SIGNUP(true, 1100, "회원가입을 마저 진행해주세요."),

    /**
     * 2000 : Request 오류
     */
    // 입력값 예외
    INVALID_REQUEST(false, 2000, "잘못된 요청이 존재합니다."),

    // 2100 : JWT 예외 - Filter에서 처리


    // 2200 : Refresh Token 예외 - Exception Handler에서 처리


    // 2300 : MemberException

    // 2400 : ReviewException

    // 2500 : RequestParam exception
    EMPTY_REQUEST_PARAMETER(false, 2098, "Request Parameter가 존재하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(false, 2099, "Request Parameter나 Path Variable의 유형이 불일치합니다."),


    // 2600 : RecordException
    NONE_RECORD(false, 2500, "해당 기록이 존재하지 않습니다."),
    ADD_RECORD_AMOUNT_ERROR(false, 2501, "기록 추가 중 데이터베이스에 오류가 발생하였습니다."),
    INVALID_YEAR(false, 2502, "조회 연도가 유효하지 않습니다."),
    INVALID_MONTH(false, 2503, "조회 월이 유효하지 않습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    /**
     * 5000 : AWS 오류
     */
    // AWS S3
    POST_IMAGE_CONVERT_ERROR(false, 5000, "사진이 없거나 변환되지 않았습니다."),
    POST_IMAGE_INVALID_EXTENSION(false, 5001, "올바른 확장자가 아닙니다."),
    IMAGE_CONVERT_ERROR(false, 5002, "사진이 없거나 변환되지 않았습니다."),
    IMAGE_INVALID_EXTENSION(false, 5003, "올바른 확장자가 아닙니다."),

    /**
     * 6000 : 보안 이슈
     */
    // 보안(리프레시 토큰 탈취) 이슈
    USING_STOLEN_TOKEN(false, 6000, "토큰이 탈취되었습니다.");

    private final boolean isSuccess;
    private final int responseCode;
    private final String responseMessage;
}
