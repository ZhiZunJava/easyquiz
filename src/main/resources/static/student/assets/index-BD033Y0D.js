import{d as U,o as I,c as Q,a as E,u as P,L as ue,_ as G,h as k,r as b,b as te,e as le,F as $,f as re,g as ce,i as fe,j as A,k as V,w as M,t as S,l as y,m as x,n as T,p as de,q as oe,M as Y,s as ve,v as he}from"./index-BN_aPgIH.js";import{u as me}from"./enumitem-C4DHP3Rz.js";const ge={class:"login-header"},pe=U({__name:"Header",setup(i){return(c,f)=>(I(),Q("header",ge,[E(P(ue),{class:"logo"})]))}}),we=G(pe,[["__scopeId","data-v-dab358b3"]]);/*!
 * qrcode.vue v3.5.1
 * A Vue.js component to generate QRCode. Both support Vue 2 and Vue 3
 * © 2017-PRESENT @scopewu(https://github.com/scopewu)
 * MIT License.
 */var L=function(){return L=Object.assign||function(c){for(var f,u=1,l=arguments.length;u<l;u++){f=arguments[u];for(var h in f)Object.prototype.hasOwnProperty.call(f,h)&&(c[h]=f[h])}return c},L.apply(this,arguments)};var B;(function(i){var c=function(){function o(e,t,r,n){if(this.version=e,this.errorCorrectionLevel=t,this.modules=[],this.isFunction=[],e<o.MIN_VERSION||e>o.MAX_VERSION)throw new RangeError("Version value out of range");if(n<-1||n>7)throw new RangeError("Mask value out of range");this.size=e*4+17;for(var s=[],a=0;a<this.size;a++)s.push(!1);for(var a=0;a<this.size;a++)this.modules.push(s.slice()),this.isFunction.push(s.slice());this.drawFunctionPatterns();var d=this.addEccAndInterleave(r);if(this.drawCodewords(d),n==-1)for(var v=1e9,a=0;a<8;a++){this.applyMask(a),this.drawFormatBits(a);var m=this.getPenaltyScore();m<v&&(n=a,v=m),this.applyMask(a)}l(0<=n&&n<=7),this.mask=n,this.applyMask(n),this.drawFormatBits(n),this.isFunction=[]}return o.encodeText=function(e,t){var r=i.QrSegment.makeSegments(e);return o.encodeSegments(r,t)},o.encodeBinary=function(e,t){var r=i.QrSegment.makeBytes(e);return o.encodeSegments([r],t)},o.encodeSegments=function(e,t,r,n,s,a){if(r===void 0&&(r=1),n===void 0&&(n=40),s===void 0&&(s=-1),a===void 0&&(a=!0),!(o.MIN_VERSION<=r&&r<=n&&n<=o.MAX_VERSION)||s<-1||s>7)throw new RangeError("Invalid value");var d,v;for(d=r;;d++){var m=o.getNumDataCodewords(d,t)*8,w=h.getTotalBits(e,d);if(w<=m){v=w;break}if(d>=n)throw new RangeError("Data too long")}for(var g=0,R=[o.Ecc.MEDIUM,o.Ecc.QUARTILE,o.Ecc.HIGH];g<R.length;g++){var p=R[g];a&&v<=o.getNumDataCodewords(d,p)*8&&(t=p)}for(var _=[],N=0,C=e;N<C.length;N++){var z=C[N];f(z.mode.modeBits,4,_),f(z.numChars,z.mode.numCharCountBits(d),_);for(var O=0,D=z.getData();O<D.length;O++){var K=D[O];_.push(K)}}l(_.length==v);var H=o.getNumDataCodewords(d,t)*8;l(_.length<=H),f(0,Math.min(4,H-_.length),_),f(0,(8-_.length%8)%8,_),l(_.length%8==0);for(var j=236;_.length<H;j^=253)f(j,8,_);for(var q=[];q.length*8<_.length;)q.push(0);return _.forEach(function(ie,ee){return q[ee>>>3]|=ie<<7-(ee&7)}),new o(d,t,q,s)},o.prototype.getModule=function(e,t){return 0<=e&&e<this.size&&0<=t&&t<this.size&&this.modules[t][e]},o.prototype.getModules=function(){return this.modules},o.prototype.drawFunctionPatterns=function(){for(var e=0;e<this.size;e++)this.setFunctionModule(6,e,e%2==0),this.setFunctionModule(e,6,e%2==0);this.drawFinderPattern(3,3),this.drawFinderPattern(this.size-4,3),this.drawFinderPattern(3,this.size-4);for(var t=this.getAlignmentPatternPositions(),r=t.length,e=0;e<r;e++)for(var n=0;n<r;n++)e==0&&n==0||e==0&&n==r-1||e==r-1&&n==0||this.drawAlignmentPattern(t[e],t[n]);this.drawFormatBits(0),this.drawVersion()},o.prototype.drawFormatBits=function(e){for(var t=this.errorCorrectionLevel.formatBits<<3|e,r=t,n=0;n<10;n++)r=r<<1^(r>>>9)*1335;var s=(t<<10|r)^21522;l(s>>>15==0);for(var n=0;n<=5;n++)this.setFunctionModule(8,n,u(s,n));this.setFunctionModule(8,7,u(s,6)),this.setFunctionModule(8,8,u(s,7)),this.setFunctionModule(7,8,u(s,8));for(var n=9;n<15;n++)this.setFunctionModule(14-n,8,u(s,n));for(var n=0;n<8;n++)this.setFunctionModule(this.size-1-n,8,u(s,n));for(var n=8;n<15;n++)this.setFunctionModule(8,this.size-15+n,u(s,n));this.setFunctionModule(8,this.size-8,!0)},o.prototype.drawVersion=function(){if(!(this.version<7)){for(var e=this.version,t=0;t<12;t++)e=e<<1^(e>>>11)*7973;var r=this.version<<12|e;l(r>>>18==0);for(var t=0;t<18;t++){var n=u(r,t),s=this.size-11+t%3,a=Math.floor(t/3);this.setFunctionModule(s,a,n),this.setFunctionModule(a,s,n)}}},o.prototype.drawFinderPattern=function(e,t){for(var r=-4;r<=4;r++)for(var n=-4;n<=4;n++){var s=Math.max(Math.abs(n),Math.abs(r)),a=e+n,d=t+r;0<=a&&a<this.size&&0<=d&&d<this.size&&this.setFunctionModule(a,d,s!=2&&s!=4)}},o.prototype.drawAlignmentPattern=function(e,t){for(var r=-2;r<=2;r++)for(var n=-2;n<=2;n++)this.setFunctionModule(e+n,t+r,Math.max(Math.abs(n),Math.abs(r))!=1)},o.prototype.setFunctionModule=function(e,t,r){this.modules[t][e]=r,this.isFunction[t][e]=!0},o.prototype.addEccAndInterleave=function(e){var t=this.version,r=this.errorCorrectionLevel;if(e.length!=o.getNumDataCodewords(t,r))throw new RangeError("Invalid argument");for(var n=o.NUM_ERROR_CORRECTION_BLOCKS[r.ordinal][t],s=o.ECC_CODEWORDS_PER_BLOCK[r.ordinal][t],a=Math.floor(o.getNumRawDataModules(t)/8),d=n-a%n,v=Math.floor(a/n),m=[],w=o.reedSolomonComputeDivisor(s),g=0,R=0;g<n;g++){var p=e.slice(R,R+v-s+(g<d?0:1));R+=p.length;var _=o.reedSolomonComputeRemainder(p,w);g<d&&p.push(0),m.push(p.concat(_))}for(var N=[],C=function(z){m.forEach(function(O,D){(z!=v-s||D>=d)&&N.push(O[z])})},g=0;g<m[0].length;g++)C(g);return l(N.length==a),N},o.prototype.drawCodewords=function(e){if(e.length!=Math.floor(o.getNumRawDataModules(this.version)/8))throw new RangeError("Invalid argument");for(var t=0,r=this.size-1;r>=1;r-=2){r==6&&(r=5);for(var n=0;n<this.size;n++)for(var s=0;s<2;s++){var a=r-s,d=(r+1&2)==0,v=d?this.size-1-n:n;!this.isFunction[v][a]&&t<e.length*8&&(this.modules[v][a]=u(e[t>>>3],7-(t&7)),t++)}}l(t==e.length*8)},o.prototype.applyMask=function(e){if(e<0||e>7)throw new RangeError("Mask value out of range");for(var t=0;t<this.size;t++)for(var r=0;r<this.size;r++){var n=void 0;switch(e){case 0:n=(r+t)%2==0;break;case 1:n=t%2==0;break;case 2:n=r%3==0;break;case 3:n=(r+t)%3==0;break;case 4:n=(Math.floor(r/3)+Math.floor(t/2))%2==0;break;case 5:n=r*t%2+r*t%3==0;break;case 6:n=(r*t%2+r*t%3)%2==0;break;case 7:n=((r+t)%2+r*t%3)%2==0;break;default:throw new Error("Unreachable")}!this.isFunction[t][r]&&n&&(this.modules[t][r]=!this.modules[t][r])}},o.prototype.getPenaltyScore=function(){for(var e=0,t=0;t<this.size;t++){for(var r=!1,n=0,s=[0,0,0,0,0,0,0],a=0;a<this.size;a++)this.modules[t][a]==r?(n++,n==5?e+=o.PENALTY_N1:n>5&&e++):(this.finderPenaltyAddHistory(n,s),r||(e+=this.finderPenaltyCountPatterns(s)*o.PENALTY_N3),r=this.modules[t][a],n=1);e+=this.finderPenaltyTerminateAndCount(r,n,s)*o.PENALTY_N3}for(var a=0;a<this.size;a++){for(var r=!1,d=0,s=[0,0,0,0,0,0,0],t=0;t<this.size;t++)this.modules[t][a]==r?(d++,d==5?e+=o.PENALTY_N1:d>5&&e++):(this.finderPenaltyAddHistory(d,s),r||(e+=this.finderPenaltyCountPatterns(s)*o.PENALTY_N3),r=this.modules[t][a],d=1);e+=this.finderPenaltyTerminateAndCount(r,d,s)*o.PENALTY_N3}for(var t=0;t<this.size-1;t++)for(var a=0;a<this.size-1;a++){var v=this.modules[t][a];v==this.modules[t][a+1]&&v==this.modules[t+1][a]&&v==this.modules[t+1][a+1]&&(e+=o.PENALTY_N2)}for(var m=0,w=0,g=this.modules;w<g.length;w++){var R=g[w];m=R.reduce(function(N,C){return N+(C?1:0)},m)}var p=this.size*this.size,_=Math.ceil(Math.abs(m*20-p*10)/p)-1;return l(0<=_&&_<=9),e+=_*o.PENALTY_N4,l(0<=e&&e<=2568888),e},o.prototype.getAlignmentPatternPositions=function(){if(this.version==1)return[];for(var e=Math.floor(this.version/7)+2,t=Math.floor((this.version*8+e*3+5)/(e*4-4))*2,r=[6],n=this.size-7;r.length<e;n-=t)r.splice(1,0,n);return r},o.getNumRawDataModules=function(e){if(e<o.MIN_VERSION||e>o.MAX_VERSION)throw new RangeError("Version number out of range");var t=(16*e+128)*e+64;if(e>=2){var r=Math.floor(e/7)+2;t-=(25*r-10)*r-55,e>=7&&(t-=36)}return l(208<=t&&t<=29648),t},o.getNumDataCodewords=function(e,t){return Math.floor(o.getNumRawDataModules(e)/8)-o.ECC_CODEWORDS_PER_BLOCK[t.ordinal][e]*o.NUM_ERROR_CORRECTION_BLOCKS[t.ordinal][e]},o.reedSolomonComputeDivisor=function(e){if(e<1||e>255)throw new RangeError("Degree out of range");for(var t=[],r=0;r<e-1;r++)t.push(0);t.push(1);for(var n=1,r=0;r<e;r++){for(var s=0;s<t.length;s++)t[s]=o.reedSolomonMultiply(t[s],n),s+1<t.length&&(t[s]^=t[s+1]);n=o.reedSolomonMultiply(n,2)}return t},o.reedSolomonComputeRemainder=function(e,t){for(var r=t.map(function(v){return 0}),n=function(v){var m=v^r.shift();r.push(0),t.forEach(function(w,g){return r[g]^=o.reedSolomonMultiply(w,m)})},s=0,a=e;s<a.length;s++){var d=a[s];n(d)}return r},o.reedSolomonMultiply=function(e,t){if(e>>>8||t>>>8)throw new RangeError("Byte out of range");for(var r=0,n=7;n>=0;n--)r=r<<1^(r>>>7)*285,r^=(t>>>n&1)*e;return l(r>>>8==0),r},o.prototype.finderPenaltyCountPatterns=function(e){var t=e[1];l(t<=this.size*3);var r=t>0&&e[2]==t&&e[3]==t*3&&e[4]==t&&e[5]==t;return(r&&e[0]>=t*4&&e[6]>=t?1:0)+(r&&e[6]>=t*4&&e[0]>=t?1:0)},o.prototype.finderPenaltyTerminateAndCount=function(e,t,r){return e&&(this.finderPenaltyAddHistory(t,r),t=0),t+=this.size,this.finderPenaltyAddHistory(t,r),this.finderPenaltyCountPatterns(r)},o.prototype.finderPenaltyAddHistory=function(e,t){t[0]==0&&(e+=this.size),t.pop(),t.unshift(e)},o.MIN_VERSION=1,o.MAX_VERSION=40,o.PENALTY_N1=3,o.PENALTY_N2=3,o.PENALTY_N3=40,o.PENALTY_N4=10,o.ECC_CODEWORDS_PER_BLOCK=[[-1,7,10,15,20,26,18,20,24,30,18,20,24,26,30,22,24,28,30,28,28,28,28,30,30,26,28,30,30,30,30,30,30,30,30,30,30,30,30,30,30],[-1,10,16,26,18,24,16,18,22,22,26,30,22,22,24,24,28,28,26,26,26,26,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28,28],[-1,13,22,18,26,18,24,18,22,20,24,28,26,24,20,30,24,28,28,26,30,28,30,30,30,30,28,30,30,30,30,30,30,30,30,30,30,30,30,30,30],[-1,17,28,22,16,22,28,26,26,24,28,24,28,22,24,24,30,28,28,26,28,30,24,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30]],o.NUM_ERROR_CORRECTION_BLOCKS=[[-1,1,1,1,1,1,2,2,2,2,4,4,4,4,4,6,6,6,6,7,8,8,9,9,10,12,12,12,13,14,15,16,17,18,19,19,20,21,22,24,25],[-1,1,1,1,2,2,4,4,4,5,5,5,8,9,9,10,10,11,13,14,16,17,17,18,20,21,23,25,26,28,29,31,33,35,37,38,40,43,45,47,49],[-1,1,1,2,2,4,4,6,6,8,8,8,10,12,16,12,17,16,18,21,20,23,23,25,27,29,34,34,35,38,40,43,45,48,51,53,56,59,62,65,68],[-1,1,1,2,4,4,4,5,6,8,8,11,11,16,16,18,16,19,21,25,25,25,34,30,32,35,37,40,42,45,48,51,54,57,60,63,66,70,74,77,81]],o}();i.QrCode=c;function f(o,e,t){if(e<0||e>31||o>>>e)throw new RangeError("Value out of range");for(var r=e-1;r>=0;r--)t.push(o>>>r&1)}function u(o,e){return(o>>>e&1)!=0}function l(o){if(!o)throw new Error("Assertion error")}var h=function(){function o(e,t,r){if(this.mode=e,this.numChars=t,this.bitData=r,t<0)throw new RangeError("Invalid argument");this.bitData=r.slice()}return o.makeBytes=function(e){for(var t=[],r=0,n=e;r<n.length;r++){var s=n[r];f(s,8,t)}return new o(o.Mode.BYTE,e.length,t)},o.makeNumeric=function(e){if(!o.isNumeric(e))throw new RangeError("String contains non-numeric characters");for(var t=[],r=0;r<e.length;){var n=Math.min(e.length-r,3);f(parseInt(e.substring(r,r+n),10),n*3+1,t),r+=n}return new o(o.Mode.NUMERIC,e.length,t)},o.makeAlphanumeric=function(e){if(!o.isAlphanumeric(e))throw new RangeError("String contains unencodable characters in alphanumeric mode");var t=[],r;for(r=0;r+2<=e.length;r+=2){var n=o.ALPHANUMERIC_CHARSET.indexOf(e.charAt(r))*45;n+=o.ALPHANUMERIC_CHARSET.indexOf(e.charAt(r+1)),f(n,11,t)}return r<e.length&&f(o.ALPHANUMERIC_CHARSET.indexOf(e.charAt(r)),6,t),new o(o.Mode.ALPHANUMERIC,e.length,t)},o.makeSegments=function(e){return e==""?[]:o.isNumeric(e)?[o.makeNumeric(e)]:o.isAlphanumeric(e)?[o.makeAlphanumeric(e)]:[o.makeBytes(o.toUtf8ByteArray(e))]},o.makeEci=function(e){var t=[];if(e<0)throw new RangeError("ECI assignment value out of range");if(e<128)f(e,8,t);else if(e<16384)f(2,2,t),f(e,14,t);else if(e<1e6)f(6,3,t),f(e,21,t);else throw new RangeError("ECI assignment value out of range");return new o(o.Mode.ECI,0,t)},o.isNumeric=function(e){return o.NUMERIC_REGEX.test(e)},o.isAlphanumeric=function(e){return o.ALPHANUMERIC_REGEX.test(e)},o.prototype.getData=function(){return this.bitData.slice()},o.getTotalBits=function(e,t){for(var r=0,n=0,s=e;n<s.length;n++){var a=s[n],d=a.mode.numCharCountBits(t);if(a.numChars>=1<<d)return 1/0;r+=4+d+a.bitData.length}return r},o.toUtf8ByteArray=function(e){e=encodeURI(e);for(var t=[],r=0;r<e.length;r++)e.charAt(r)!="%"?t.push(e.charCodeAt(r)):(t.push(parseInt(e.substring(r+1,r+3),16)),r+=2);return t},o.NUMERIC_REGEX=/^[0-9]*$/,o.ALPHANUMERIC_REGEX=/^[A-Z0-9 $%*+.\/:-]*$/,o.ALPHANUMERIC_CHARSET="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:",o}();i.QrSegment=h})(B||(B={}));(function(i){(function(c){var f=function(){function u(l,h){this.ordinal=l,this.formatBits=h}return u.LOW=new u(0,1),u.MEDIUM=new u(1,0),u.QUARTILE=new u(2,3),u.HIGH=new u(3,2),u}();c.Ecc=f})(i.QrCode||(i.QrCode={}))})(B||(B={}));(function(i){(function(c){var f=function(){function u(l,h){this.modeBits=l,this.numBitsCharCount=h}return u.prototype.numCharCountBits=function(l){return this.numBitsCharCount[Math.floor((l+7)/17)]},u.NUMERIC=new u(1,[10,12,14]),u.ALPHANUMERIC=new u(2,[9,11,13]),u.BYTE=new u(4,[8,16,16]),u.KANJI=new u(8,[8,10,12]),u.ECI=new u(7,[0,0,0]),u}();c.Mode=f})(i.QrSegment||(i.QrSegment={}))})(B||(B={}));var F=B,W="L",X={L:F.QrCode.Ecc.LOW,M:F.QrCode.Ecc.MEDIUM,Q:F.QrCode.Ecc.QUARTILE,H:F.QrCode.Ecc.HIGH},_e=function(){try{new Path2D().addPath(new Path2D)}catch{return!1}return!0}();function J(i){return i in X}function ne(i,c){c===void 0&&(c=0);var f=[];return i.forEach(function(u,l){var h=null;u.forEach(function(o,e){if(!o&&h!==null){f.push("M".concat(h+c," ").concat(l+c,"h").concat(e-h,"v1H").concat(h+c,"z")),h=null;return}if(e===u.length-1){if(!o)return;h===null?f.push("M".concat(e+c,",").concat(l+c," h1v1H").concat(e+c,"z")):f.push("M".concat(h+c,",").concat(l+c," h").concat(e+1-h,"v1H").concat(h+c,"z"));return}o&&h===null&&(h=e)})}),f.join("")}function ae(i,c,f,u){var l=u.width,h=u.height,o=u.x,e=u.y,t=i.length+f*2,r=Math.floor(c*.1),n=t/c,s=(l||r)*n,a=(h||r)*n,d=o==null?i.length/2-s/2:o*n,v=e==null?i.length/2-a/2:e*n,m=null;if(u.excavate){var w=Math.floor(d),g=Math.floor(v),R=Math.ceil(s+d-w),p=Math.ceil(a+v-g);m={x:w,y:g,w:R,h:p}}return{x:d,y:v,h:a,w:s,excavation:m}}function se(i,c){return i.slice().map(function(f,u){return u<c.y||u>=c.y+c.h?f:f.map(function(l,h){return h<c.x||h>=c.x+c.w?l:!1})})}var Z={value:{type:String,required:!0,default:""},size:{type:Number,default:100},level:{type:String,default:W,validator:function(i){return J(i)}},background:{type:String,default:"#fff"},foreground:{type:String,default:"#000"},margin:{type:Number,required:!1,default:0},imageSettings:{type:Object,required:!1,default:function(){return{}}}},Ee=L(L({},Z),{renderAs:{type:String,required:!1,default:"canvas",validator:function(i){return["canvas","svg"].indexOf(i)>-1}}}),Ce=U({name:"QRCodeSvg",props:Z,setup:function(i){var c=b(0),f=b(""),u,l=function(){var h=i.value,o=i.level,e=i.margin,t=e>>>0,r=J(o)?o:W,n=F.QrCode.encodeText(h,X[r]).getModules();if(c.value=n.length+t*2,i.imageSettings.src){var s=ae(n,i.size,t,i.imageSettings);u={x:s.x+t,y:s.y+t,width:s.w,height:s.h},s.excavation&&(n=se(n,s.excavation))}f.value=ne(n,t)};return l(),te(l),function(){return k("svg",{width:i.size,height:i.size,"shape-rendering":"crispEdges",xmlns:"http://www.w3.org/2000/svg",viewBox:"0 0 ".concat(c.value," ").concat(c.value)},[k("path",{fill:i.background,d:"M0,0 h".concat(c.value,"v").concat(c.value,"H0z")}),k("path",{fill:i.foreground,d:f.value}),i.imageSettings.src&&k("image",L({href:i.imageSettings.src},u))])}}}),Me=U({name:"QRCodeCanvas",props:Z,setup:function(i,c){var f=b(null),u=b(null),l=function(){var o=i.value,e=i.level,t=i.size,r=i.margin,n=i.background,s=i.foreground,a=r>>>0,d=J(e)?e:W,v=f.value;if(v){var m=v.getContext("2d");if(m){var w=F.QrCode.encodeText(o,X[d]).getModules(),g=w.length+a*2,R=u.value,p={x:0,y:0,width:0,height:0},_=i.imageSettings.src&&R!=null&&R.naturalWidth!==0&&R.naturalHeight!==0;if(_){var N=ae(w,i.size,a,i.imageSettings);p={x:N.x+a,y:N.y+a,width:N.w,height:N.h},N.excavation&&(w=se(w,N.excavation))}var C=window.devicePixelRatio||1,z=t/g*C;v.height=v.width=t*C,m.scale(z,z),m.fillStyle=n,m.fillRect(0,0,g,g),m.fillStyle=s,_e?m.fill(new Path2D(ne(w,a))):w.forEach(function(O,D){O.forEach(function(K,H){K&&m.fillRect(H+a,D+a,1,1)})}),_&&m.drawImage(R,p.x,p.y,p.width,p.height)}}};le(l),te(l);var h=c.attrs.style;return function(){return k($,[k("canvas",L(L({},c.attrs),{ref:f,style:L(L({},h),{width:"".concat(i.size,"px"),height:"".concat(i.size,"px")})})),i.imageSettings.src&&k("img",{ref:u,src:i.imageSettings.src,style:{display:"none"},onLoad:l})])}}}),Re=U({name:"Qrcode",render:function(){var i=this.$props,c=i.renderAs,f=i.value,u=i.size,l=i.margin,h=i.level,o=i.background,e=i.foreground,t=i.imageSettings;return k(c==="svg"?Ce:Me,{value:f,size:u,margin:l,level:h,background:o,foreground:e,imageSettings:t})},props:Ee});const Ne={class:"check-container remember-pwd"},Ae={class:"tip-container"},be={class:"tip"},ye={class:"refresh"},Pe=U({__name:"Login",setup(i){const c=re(),f={userName:"",password:"",remember:!1},u={phone:[{required:!0,message:"请输入手机号",type:"error"}],userName:[{required:!0,message:"请输入账号",type:"error"}],password:[{required:!0,message:"请输入密码",type:"error"}],verifyCode:[{required:!0,message:"请输入验证码",type:"error"}]},l=b("password"),h=b(),o=b({...f}),e=b(!1),t=ce(),r=fe(),n=async s=>{if(s.validateResult===!0)try{await c.login(o.value),Y.success("登录成功");const a=r.query.redirect,d=a?decodeURIComponent(a):"/dashboard";t.push(d)}catch(a){console.log(a),a instanceof Error&&Y.error(a.message)}};return(s,a)=>{const d=A("t-icon"),v=A("t-input"),m=A("t-form-item"),w=A("t-checkbox"),g=A("t-button"),R=A("t-form");return I(),V(R,{ref_key:"form",ref:h,class:oe(["item-container",`login-${l.value}`]),data:o.value,rules:u,"label-width":"0",onSubmit:n},{default:M(()=>[l.value=="password"?(I(),Q($,{key:0},[E(m,{name:"account"},{default:M(()=>[E(v,{modelValue:o.value.userName,"onUpdate:modelValue":a[0]||(a[0]=p=>o.value.userName=p),size:"large",placeholder:`${P(S)("pages.login.input.account")}`},{"prefix-icon":M(()=>[E(d,{name:"user"})]),_:1},8,["modelValue","placeholder"])]),_:1}),E(m,{name:"password"},{default:M(()=>[E(v,{modelValue:o.value.password,"onUpdate:modelValue":a[2]||(a[2]=p=>o.value.password=p),size:"large",type:e.value?"text":"password",clearable:"",placeholder:`${P(S)("pages.login.input.password")}`},{"prefix-icon":M(()=>[E(d,{name:"lock-on"})]),"suffix-icon":M(()=>[E(d,{name:e.value?"browse":"browse-off",onClick:a[1]||(a[1]=p=>e.value=!e.value)},null,8,["name"])]),_:1},8,["modelValue","type","placeholder"])]),_:1}),y("div",Ne,[E(w,{modelValue:o.value.remember,"onUpdate:modelValue":a[3]||(a[3]=p=>o.value.remember=p)},{default:M(()=>[x(T(P(S)("pages.login.remember")),1)]),_:1},8,["modelValue"])])],64)):l.value=="qrcode"?(I(),Q($,{key:1},[y("div",Ae,[y("span",be,T(P(S)("pages.login.wechatLogin")),1),y("span",ye,[x(T(P(S)("pages.login.refresh"))+" ",1),E(d,{name:"refresh"})])]),E(Re,{value:"",size:160,level:"H"})],64)):(I(),Q($,{key:2},[],64)),l.value!=="qrcode"?(I(),V(m,{key:3,class:"btn-container"},{default:M(()=>[E(g,{block:"",size:"large",type:"submit"},{default:M(()=>[x(T(P(S)("pages.login.signIn")),1)]),_:1})]),_:1})):de("",!0)]),_:1},8,["class","data"])}}}),Ie=G(Pe,[["__scopeId","data-v-4b20f49d"]]),Se=U({__name:"Register",emits:["registerSuccess"],setup(i,{emit:c}){const f=re(),u=me(),l=ve(()=>u.user.levelEnum),h={userName:"",password:"",userLevel:1},o={userName:[{required:!0,message:"请输入用户名",type:"error",trigger:"blur"}],password:[{required:!0,message:"请输入密码",type:"error",trigger:"blur"}],userLevel:[{required:!0,message:"请选择年级",type:"error",trigger:"change"}]},e=b("phone"),t=b(),r=b({...h}),n=b(!1),s=c,a=async d=>{if(d.validateResult===!0)try{await f.register(r.value),Y.success("注册成功"),s("registerSuccess")}catch(v){console.log(v),v instanceof Error&&Y.error(v.message)}};return(d,v)=>{const m=A("t-icon"),w=A("t-input"),g=A("t-form-item"),R=A("t-option"),p=A("t-select"),_=A("t-button"),N=A("t-form");return I(),V(N,{ref_key:"form",ref:t,class:oe(["item-container",`register-${e.value}`]),data:r.value,rules:o,"label-width":"0",onSubmit:a},{default:M(()=>[E(g,{name:"userName"},{default:M(()=>[E(w,{modelValue:r.value.userName,"onUpdate:modelValue":v[0]||(v[0]=C=>r.value.userName=C),size:"large",placeholder:"请输入您的用户名",autocomplete:"off"},{"prefix-icon":M(()=>[E(m,{name:"user"})]),_:1},8,["modelValue"])]),_:1}),E(g,{name:"password"},{default:M(()=>[E(w,{modelValue:r.value.password,"onUpdate:modelValue":v[2]||(v[2]=C=>r.value.password=C),size:"large",type:n.value?"text":"password",clearable:"",placeholder:"请输入登录密码",autocomplete:"off"},{"prefix-icon":M(()=>[E(m,{name:"lock-on"})]),"suffix-icon":M(()=>[E(m,{name:n.value?"browse":"browse-off",onClick:v[1]||(v[1]=C=>n.value=!n.value)},null,8,["name"])]),_:1},8,["modelValue","type"])]),_:1}),E(g,{class:"verification-code",name:"userLevel"},{default:M(()=>[E(p,{modelValue:r.value.userLevel,"onUpdate:modelValue":v[3]||(v[3]=C=>r.value.userLevel=C),size:"large",class:"form-item-content",placeholder:"请选择年级",clearable:""},{"prefix-icon":M(()=>[E(m,{name:"education"})]),default:M(()=>[v[4]||(v[4]=x(" > ")),(I(!0),Q($,null,he(l.value,C=>(I(),V(R,{key:C.key,label:C.value,value:C.key},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),E(g,null,{default:M(()=>[E(_,{block:"",size:"large",type:"submit"},{default:M(()=>v[5]||(v[5]=[x(" 注册 ")])),_:1})]),_:1})]),_:1},8,["class","data"])}}}),ze=G(Se,[["__scopeId","data-v-b0428643"]]),Le={class:"login-wrapper"},ke={class:"login-container"},Oe={class:"title-container"},Te={class:"title margin-no"},Be={class:"sub-title"},Ue={class:"tip"},De={name:"LoginIndex"},Fe=U({...De,setup(i){const c=b("login"),f=u=>{c.value=u};return(u,l)=>(I(),Q("div",Le,[E(we),y("div",ke,[y("div",Oe,[y("h1",Te,T(P(S)("pages.login.loginTitle")),1),l[2]||(l[2]=y("h1",{class:"title"},"轻松考 考试系统",-1)),y("div",Be,[y("p",Ue,T(c.value=="register"?P(S)("pages.login.existAccount"):P(S)("pages.login.noAccount")),1),y("p",{class:"tip",onClick:l[0]||(l[0]=h=>f(c.value=="register"?"login":"register"))},T(c.value=="register"?P(S)("pages.login.signIn"):P(S)("pages.login.createAccount")),1)])]),c.value==="login"?(I(),V(Ie,{key:0})):(I(),V(ze,{key:1,onRegisterSuccess:l[1]||(l[1]=h=>f("login"))}))]),l[3]||(l[3]=y("footer",{class:"copyright"},"Copyright @ 2025 EasyQuiz. All Rights Reserved",-1))]))}}),He=G(Fe,[["__scopeId","data-v-2a124c5a"]]);export{He as default};
