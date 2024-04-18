<script setup>
    import router from "@/router/router";
    import { reactive, ref, onMounted } from "vue";
    import { useRoute } from 'vue-router';
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
    
    const route = useRoute();
    const id = route.params.id;

    const state = reactive({
        category: [],
        exams: [],
        goals: [],
        studyclub: {}
    })

    const name = ref();
    const introduce = ref();
    const memberLimit = ref();
    const selectedCategory = ref();
    const selectedExam = ref();
    const selectedGoal = ref();

    const fetchCategory = async() => {

        try {
            const response = await fetch('/api/studyclub/category');
            // 작성자 id 경로에 추후 추가

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.category = data;

        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    };

    const fetchExams = async() => {

        try {
            const response = await fetch(`/api/studyclub/exam/${selectedCategory.value}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.exams = data;
            
        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }


    const fetchGoals = async() => {

        try {
            const response = await fetch(`/api/studyclub/goal/${selectedCategory.value}`);

            if(!response.ok) {
                throw new Error('response is not ok');
            }

            const data = await response.json();
            state.goals = data;
            
        } catch(error) {
            console.error('fetch error: ' + error.message);
        }
    }

    const registStudyClub = async() => {
        
        const postData = {
            name: name.value,
            introduce: introduce.value, 
            memberLimit: memberLimit.value,
            studyId: selectedCategory.value,
            examId: selectedExam.value,
            goalId: selectedGoal.value
        }

        try {
            const response = await fetch(`/api/studyclub/regist/${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                    body: JSON.stringify(postData)
            });

            if(!response) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            state.studyclub = data;
            
        } catch(error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }

        const postData2 = {
            "memberId": id,
            "studyclubId": state.studyclub.id,
            "memberType": "L"
        }

        try {
            const response = await fetch(`/api/studyclubMember/insertMember`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                    body: JSON.stringify(postData2)
            });


            console.log(postData);
            console.log(postData2);

            if(!response) {
                throw new Error('Network response was not ok');
            }
            alert('스터디클럽이 생성되었습니다!');
            alert('모집글 작성 페이지로 이동합니다.');
            
            router.push(`/recruit-regist/${state.studyclub["id"]}`);
            
        } catch(error) {
            console.error('There was a problem with the fetch operation:', error.message);
        }

    };

    onMounted(async() => {
        await fetchCategory();
    });
</script>

<template>
    <div class="all">

    <Header></Header>
    
        <div class="wrapper">
            <div class="container">
                <div class="title">스터디클럽 등록</div>
                <div class="info">
                    <div class="name">스터디클럽 이름: 
                        <input class="content" v-model="name"/>
                    </div>
                    <div class="introduce">스터디클럽 소개: </div>
                        <textarea class="content" cols="50" rows="5" v-model="introduce"/>
                    <div class="number">스터디클럽 정원: 
                        <input type="number" class="content" v-model="memberLimit"/>
                    </div>
                    <div class="category">스터디클럽 카테고리: 
                        <select class="content" v-model="selectedCategory" @change="fetchGoals(), fetchExams()">
                            <option v-for="item in state.category" :value="item.id"> {{ item.studyName }} </option>
                        </select>
                    </div>
                    <div class="date">스터디클럽 시험일: 
                        <select class="content" v-model="selectedExam">
                            <option v-for="item in state.exams" :value="item.id"> {{ item.examDate.substring(0, 10) }} </option>
                        </select>
                    </div>
                    <div class="goal">스터디클럽 목표 점수: 
                        <select class="content" v-model="selectedGoal">
                            <option v-for="item in state.goals" :value="item.id"> {{ item.score }} </option>
                        </select>
                    </div>
                    <div class="submit" @click="registStudyClub">
                        <button>등록하기</button>
                    </div>
                </div> 
            </div>
        </div>
    
    <Footer></Footer>
</div>
</template>

<style scoped>
   .container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin: 30px;
   }
   .wrapper {
    margin-left:12.5%;
    margin-right:12.5%;
    width:75%;
    display: grid;
}
   .all {
        display: grid;
        grid-template-rows: 100px minmax(780px, auto) 200px;
        align-items: center;
    }

    .title {
        font-size: 30px;
        text-align: center;
        margin-top: 50px;
    }
    .info {
        margin: 40px;
        align-items: center;
    }
    .info div {
        margin: 10px;
    }
    .content {
        margin-left: 10px;
    }
    .submit {
        display: flex;
        justify-content: center;
        padding: 20px;
    }
</style>