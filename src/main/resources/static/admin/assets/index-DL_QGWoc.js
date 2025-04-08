import{d as P,Q as oe,r as d,v as M,R as U,j as n,o as y,k as $,w as a,a as t,m as f,c as F,C as Q,F as G,T as D,M as w,e as ne,n as L,u as q,t as K,l as se,_ as ue}from"./index-DXJQNOID.js";import{u as H}from"./enumitem-j20-ZoDl.js";const re=P({__name:"DialogSubjectForm",props:{visible:{type:Boolean,default:!1},data:Object},emits:["update:visible","refresh"],setup(T,{emit:S}){const h={id:null,name:"",level:void 0,levelName:void 0},m=oe("form"),v=d(!1),s=d({...h}),x=H(),_=M(()=>x.user.levelEnum),b=S,g=({validateResult:r,firstError:l})=>{l?(console.log("Errors: ",r),w.warning(l)):(s.value.levelName=x.enumFormat(_.value,s.value.level),D.edit(s.value).then(p=>{w.success(s.value.id?"修改成功":"添加成功"),v.value=!1,b("refresh")}).catch(p=>{console.log("Errors: ",p),w.warning(p.message)}))},C=()=>{v.value=!1,s.value={...h}};U(()=>v.value,r=>{b("update:visible",r)}),U(()=>T.visible,r=>{v.value=r}),U(()=>T.data,r=>{r&&(s.value=r)});const k={name:[{required:!0,message:"请输入学科名称",type:"error",trigger:"blur"}],level:[{required:!0,message:"请选择学科年级",type:"error",trigger:"blur"}]};return(r,l)=>{const p=n("t-input"),c=n("t-form-item"),N=n("t-option"),E=n("t-select"),V=n("t-button"),I=n("t-form"),j=n("t-dialog");return y(),$(j,{ref_key:"form",ref:m,visible:v.value,"onUpdate:visible":l[2]||(l[2]=i=>v.value=i),header:s.value.id?"修改学科":"新增学科",width:680,footer:!1},{body:a(()=>[t(I,{ref_key:"form",ref:m,data:s.value,rules:k,"label-width":100,onSubmit:g},{default:a(()=>[t(c,{label:"用户名",name:"name"},{default:a(()=>[t(p,{modelValue:s.value.name,"onUpdate:modelValue":l[0]||(l[0]=i=>s.value.name=i),style:{width:"480px"},autocomplete:"off",placeholder:"学科名称"},null,8,["modelValue"])]),_:1}),t(c,{label:"学科年级",name:"level"},{default:a(()=>[t(E,{modelValue:s.value.level,"onUpdate:modelValue":l[1]||(l[1]=i=>s.value.level=i),class:"form-item-content",placeholder:"请选择年级",style:{width:"480px"}},{default:a(()=>[l[3]||(l[3]=f(" > ")),(y(!0),F(G,null,Q(_.value,i=>(y(),$(N,{key:i.key,label:i.value,value:i.key},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),t(c,{style:{float:"right"}},{default:a(()=>[t(V,{variant:"outline",onClick:C},{default:a(()=>l[4]||(l[4]=[f("取消")])),_:1}),t(V,{theme:"primary",type:"submit"},{default:a(()=>l[5]||(l[5]=[f("确定")])),_:1})]),_:1})]),_:1},8,["data"])]),_:1},8,["visible","header"])}}}),ie={class:"list-common-table"},de={class:"table-container"},me="id",ce="top",ve=!0,fe=P({__name:"index",setup(T){const S=H(),h=M(()=>S.user.levelEnum),m=d({level:null}),v={id:null,name:"",level:void 0,levelName:void 0},s=d([]),x=d(0),_=d(-1),b=d(!1),g=d(!1),C=d(!1),k=d({...v}),r=[{title:"ID",colKey:"id",width:100},{title:"学科名称",colKey:"name",width:150},{title:"学科年级",colKey:"levelName",width:150},{align:"left",fixed:"right",width:160,colKey:"op",title:"操作"}],l=d({defaultPageSize:20,total:100,defaultCurrent:1}),p=o=>{var e;(e=o.pagination)!=null&&e.current&&(l.value.defaultCurrent=o.pagination.current,c())},c=async()=>{b.value=!0;try{const o={...m.value,pageIndex:l.value.defaultCurrent,pageSize:l.value.defaultPageSize};Object.keys(o).forEach(e=>{o[e]===""&&(o[e]=null)}),await D.pageList(o).then(e=>{s.value=e.list,x.value=e.total,l.value={...l.value,total:e.total}})}catch(o){console.log(o)}finally{b.value=!1}},N=()=>{Object.keys(m.value).forEach(o=>{m.value[o]===""&&(m.value[o]=null)}),l.value.defaultCurrent=1,c()},E=()=>{l.value.defaultCurrent=1,c()},V=o=>{_.value=o,g.value=!0},I=()=>{_.value=-1},j=M(()=>"删除后，该学科的所有信息将被清空，且无法恢复"),i=()=>{I()},J=async()=>{await D.deleteSubject(_.value).then(()=>{g.value=!1,w.success("删除成功"),I(),c()}).catch(o=>{w.error(o)})},O=o=>{o?D.select(o).then(e=>{k.value=e}):k.value={...v},C.value=!0};return ne(()=>{c()}),(o,e)=>{const W=n("t-option"),X=n("t-select"),Y=n("t-form-item"),A=n("t-col"),R=n("t-row"),B=n("t-button"),Z=n("t-form"),z=n("t-link"),ee=n("t-space"),te=n("t-table"),le=n("t-dialog");return y(),F("div",ie,[t(Z,{ref:"form",data:m.value,"label-width":80,colon:"",onReset:E,onSubmit:N},{default:a(()=>[t(R,null,{default:a(()=>[t(A,{span:10},{default:a(()=>[t(R,{gutter:[24,24]},{default:a(()=>[t(A,{span:4},{default:a(()=>[t(Y,{label:"年级",name:"level"},{default:a(()=>[t(X,{modelValue:m.value.level,"onUpdate:modelValue":e[0]||(e[0]=u=>m.value.level=u),class:"form-item-content",placeholder:"年级",clearable:""},{default:a(()=>[e[5]||(e[5]=f(" > ")),(y(!0),F(G,null,Q(h.value,u=>(y(),$(W,{key:u.key,label:u.value,value:u.key},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1}),t(A,{span:2,class:"operation-container"},{default:a(()=>[t(B,{theme:"primary",onClick:e[1]||(e[1]=u=>O())},{default:a(()=>[f(L(q(K)("components.commonTable.add")),1)]),_:1}),t(B,{theme:"primary",type:"submit"},{default:a(()=>[f(L(q(K)("components.commonTable.query")),1)]),_:1}),t(B,{type:"reset",variant:"base",theme:"default"},{default:a(()=>[f(L(q(K)("components.commonTable.reset")),1)]),_:1})]),_:1})]),_:1})]),_:1},8,["data"]),se("div",de,[t(te,{data:s.value,columns:r,"row-key":me,"vertical-align":ce,hover:ve,pagination:l.value,loading:b.value,onChange:p},{op:a(u=>[t(ee,null,{default:a(()=>[t(z,{theme:"primary",onClick:ae=>O(u.row.id)},{default:a(()=>e[6]||(e[6]=[f(" 编辑 ")])),_:2},1032,["onClick"]),t(z,{theme:"danger",onClick:ae=>V(u.row.id)},{default:a(()=>e[7]||(e[7]=[f(" 删除")])),_:2},1032,["onClick"])]),_:2},1024)]),_:1},8,["data","pagination","loading"]),t(le,{visible:g.value,"onUpdate:visible":e[2]||(e[2]=u=>g.value=u),header:"确认删除当前所选学科？",body:j.value,"on-cancel":i,onConfirm:J},null,8,["visible","body"]),t(re,{visible:C.value,"onUpdate:visible":e[3]||(e[3]=u=>C.value=u),data:k.value,onRefresh:e[4]||(e[4]=u=>c())},null,8,["visible","data"])])])}}}),be=ue(fe,[["__scopeId","data-v-cf4c9caf"]]);export{be as default};
