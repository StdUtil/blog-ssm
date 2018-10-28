<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/editor.md/css/editormd.css"/>
    <link rel="stylesheet" href="/static/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/custom/admin/css/article.css"/>
    <script src="/static/jquery/jquery.min.js"></script>
    <script src="/static/layui/layui.js"></script>
    <script src="/static/editor.md/editormd.min.js"></script>
    <meta name="keywords" content="${Application.setting.keywords}"/>
    <meta name="description" content="${Application.setting.description}"/>
    <link rel="icon" href="${Application.setting.head}" />
    <title>${Application.setting.title}</title>
</head>
<body>
<div class="layui-row">
    <div class="layui-col-md-offset3 layui-col-md3">
        <input type="text" name="title" id="title" required placeholder="请输入标题" class="layui-input">
    </div>
    <div class="layui-col-md1">
        <input type="text" name="article-code" id="article-code" required placeholder="请输入文章编码" class="layui-input">
    </div>
    <div class="layui-col-md1">
        <div class="layui-form">
            <select id="category-select">
            </select>
        </div>
    </div>
    <div class="layui-col-md1">
        <button class="layui-btn layui-btn layui-btn-normal" id="save-btn">发布</button>
    </div>
</div>
<div id="layout">
    <div id="editormd">
    </div>
</div>
<script src="/static/custom/admin/js/article.js"></script>
</body>
</html>