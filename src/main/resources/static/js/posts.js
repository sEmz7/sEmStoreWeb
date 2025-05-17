let stompClient = null;

function renderPost(post) {
    const div = document.createElement('div');
    div.id = 'post-' + post.id;
    div.innerHTML = `
        <p>${post.content}</p>
        <button onclick="likePost(${post.id})">👍 ${post.likes}</button>
        <button onclick="dislikePost(${post.id})">👎 ${post.dislikes}</button>
        <hr/>
    `;
    return div;
}

function createPost() {
    const content = document.getElementById('post-content').value;
    axios.post('/api/posts', { content })
        .then(res => {
            document.getElementById('post-content').value = '';
            // WebSocket сам отобразит пост
        });
}

function likePost(id) {
    axios.post(`/api/posts/${id}/like`);
}

function dislikePost(id) {
    axios.post(`/api/posts/${id}/dislike`);
}

function connectWebSocket() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/posts', (msg) => {
            const post = JSON.parse(msg.body);
            let postElem = document.getElementById('post-' + post.id);
            if (postElem) {
                // Обновляем лайки/дизлайки
                const buttons = postElem.querySelectorAll('button');
                buttons[0].innerText = `👍 ${post.likes}`;
                buttons[1].innerText = `👎 ${post.dislikes}`;
            } else {
                // Добавляем новый пост
                const postDiv = renderPost(post);
                const container = document.getElementById('posts-container');
                container.prepend(postDiv);
            }
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    connectWebSocket();
});
