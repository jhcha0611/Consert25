import React from 'react'
import "../styles/Home.css";
import magnifier from "../assets/magnifier.png"; 
const Home = () => {
return (
<div>
    {/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡ회원가입창ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */}
    <div className="logo">TAG</div>
    
    <div className="signup_box">


        <form className="signup_upbox">
            
            <div className="section">
                <input type="text" placeholder="아이디" name="ID" />
            </div>
            
            <div className="line"></div>

            <div className="section">
                <input type="password" placeholder="비밀번호" name="password" />
            </div>

            <div className="line"></div>

            <div className="section">
                <input type="password" placeholder="비밀번호 확인" name="passwordCheck" />
            </div>

            <div className="line"></div>

            <div className="section">
                <input type="text" placeholder="이메일" name="email" />
            </div>

            

        </form>

        <form className="signup_downbox">
            <div className="section">
                <input type="text" placeholder="아이디" name="ID" />
            </div>
            
            <div className="line"></div>

            <div className="section">
                <input type="password" placeholder="비밀번호" name="password" />
            </div>

            <div className="line"></div>

            <div className="section">
                <input type="password" placeholder="비밀번호 확인" name="passwordCheck" />
            </div>

            <div className="line"></div>

            <div className="section">
                <input type="text" placeholder="이메일" name="email" />
            </div>
        </form>

        <div className="checkbox">
            <label className="checkbox_label">
                <input type="checkbox" className="checkbox_input" name="agree" />
                개인정보 수집 이용 동의
            </label>
        </div>

        <button className="signup_button">
            회 원 가 입
        </button>

        
    </div>
{/* ㅡㅡㅡㅡ로그인창ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */}

    <div className="logo">TAG</div>

    <div className="signup_box">


        <form className="login_box">
            
            <div className="section">
                <input type="text" placeholder="아이디" name="ID" />
            </div>
            
            <div className="line"></div>

            <div className="section">
                <input type="password" placeholder="비밀번호" name="password" />
            </div>

        </form>

        

        <div className="login_etc">
            <div className="finding_password">비밀번호 찾기</div>
            <div className="login_stick">|</div>
            <div className='finding_id'>아이디 찾기</div>
            <div className="login_stick">|</div>
            <div className='signup_f'>회원가입</div>
        </div>

        <button className="login_button">
            로 그 인
        </button>

        
    </div>



{/* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ관람 후기 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */}

<div className='showreview_head'>

    <div className='logo_and_search'>
        <div className="logo">TAG</div>
        <div className='search'><img className='showreview_magnifier' src={magnifier} alt='돋보기'/></div>
    </div>


    <div className="showreview_menu">
        <div className="showreview_showreview">관람 후기</div>
        <div className="showreview_stick">|</div>
        <div className="showreview_bookingguide">예매/관람 안내</div>
        <div className="showreview_stick">|</div>
        <div className="showreview_customerservice">고객 센터</div>
        <div className="showreview_stick">|</div>
        <div className="showreview_mypage">마이 페이지</div>
    </div>

</div>



</div>



)
}

export default Home
