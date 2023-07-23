package smallshop.smallshop.domain;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    // 생성 메소드 //
    public static MemberForm createMemberForm(String name, String city, String street, String zipcode) {
        MemberForm memberForm = new MemberForm();
        memberForm.setName(name);
        memberForm.setCity(city);
        memberForm.setStreet(street);
        memberForm.setZipcode(zipcode);
        return memberForm;
    }
}