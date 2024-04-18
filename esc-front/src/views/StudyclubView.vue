<template>
    <div>
    <Header></Header>
    <div class="wrapper">
        <StudyclubInfo></StudyclubInfo>
        <StudyclubContent></StudyclubContent>
    </div>
    
    <Footer></Footer>
</div>
</template>

<script setup>
    import StudyclubInfo from "@/components/Studyclub/StudyclubInfo.vue";
    import StudyclubContent from "@/components/Studyclub/StudyclubContent.vue";
    import Header from "@/components/Header/Header.vue";
    import Footer from "@/components/Footer/Footer.vue";
</script>

<style scoped>
.wrapper {
    margin-left:12.5%;
    margin-right:12.5%;
    width:75%;
    display: grid;
    height: fit-content
}
.all {
    display: grid;
    grid-template-rows: 100px minmax(780px, auto) 200px;
    align-items: center;
}
</style>