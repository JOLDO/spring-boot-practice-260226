async function get1(bno) {
    //화면에서 axios ㄷ도구 이횽해서 서버 비동기 통신 호출 후 게시글에 대한 댓글 목록 조회
    const result = await axios.get(`/_5_260306/replies/list/${bno}`)
    //결과 확인
    // console.log(result)
    return result.data;
}

// async function getList({bno,page,size,goLast}) {
//     const result = await axios.get(`/_5_260306/replies/list/${bno}`, {params: {page, size}})
// // 여기 안에, 댓글의 목록인 dtoList 가 포함되어 있다.
//
//     //마지막으로 이동 준비
//     if(goLast) {
//         const total = result.data.total;
//         const lastPage = parseInt(Math.ceil(total/size))
//         return getList({bno:bno, page:lastPage, size:size});
//     }
//
//     return result.data
// }

// 화면에 출력하기 위한 함수, : 그림을 그리는 함수
// function printReplies(page, size, goLast) {
//     // 임시 테스트, 각자 게시글 번호에서, 댓글이 있는 부모 게시글 번호 이용하기.
//     const bnoValue = typeof bno !== 'undefined' ? bno : 132;
//     getList({bno: bnoValue, page, size, goLast}).then(
//
//         data => {
//             // 먼저 댓글 목록을 그리기
//             // 이 함수는 read.html 내부에 존재함.
//             printList(data.dtoList)
//             // 댓글의 페이지네이션 그려주는 함수 호출
//             printPages(data)
//         }
//     )
// }

// 수정,
async function getList({bno, page, size, goLast}){
// 서버로부터 응답 받은 , 댓글의 목록입니다. (bno 부모 게시글에 따른 댓글 목록)
    const result = await axios.get(`/_5_260306/replies/list/${bno}`, {params: {page,size}})
    if(goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }
    return result.data
}

// 위에서, 화면을 그려주는 함수 호출해서, 실제 그림 그리기.
// 임의로 그리기.
// printReplies(1,10, true)

//댓글 등록 함수
async function addReply(replyObj) {
    const response = await axios.post(`/_5_260306/replies/`, replyObj);
    return response.data;
}

// 댓글 조회 함수
async function getReply(rno){
    const response = await axios.get(`/_5_260306/replies/${rno}`)
    return response.data
}

// 댓글 수정 함수
async function modifyReply(replyObj) {
    const response = await axios.put(`/_5_260306/replies/${replyObj.rno}`, replyObj)
    return response.data
}

// 댓글 삭제 함수
async function removeReply(rno) {
    const response = await axios.delete(`/_5_260306/replies/${rno}`)
    return response.data
}