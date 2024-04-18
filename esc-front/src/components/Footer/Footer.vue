<template>
    <footer class="footer">
        <div class="leftthings">
        <p>
            <span>홈 이용약관 개인정보처리방침</span>
        </p>
        <p>
            <span> *ESC에서 제공하는 모든 컨텐츠는 저작권법에 보호받는 저작물로서</span><br>
            <span> 무단으로 복제 또는 배포하는 경우에는 저작권법에 의하여 처벌을 받을 수 있습니다.</span>
        </p>
        <p>
            <span> © 2024 ESC.All Rights Reserved.</span>
        </p>
            
        </div>
        <div class="rightthings">
            <p>
                <span class="git">Git Hub</span><br>
                <span>https://github.com</span>
            </p>
            <p>
                <span class="title">개발자</span><br>
                <span>박고은 백동현 이예원 정우진 정태원 </span>
            </p>
        </div>
    </footer>
</template>

<script setup>

</script>

<style scoped>
    .footer {
        position: relative;
        width: 100%;
        height: 200px;
        background-color: white;
        display: grid;
        grid-template-columns: 2fr 2fr ;
        justify-items: center;
    }

    .rightthings{
        margin-right: 12.5%;
        margin-top:2%;
        font-size: 13px;
    }

    .leftthings{
        margin-left:12.5%;
        margin-top:2%;
        font-size: 13px;
    }

    .git{
        font-weight: bold;
    }

    .title{
        font-weight: bold;
    }

</style>