declare var require: any;
const vis: any = require("vis");
const tree: any = require("../../result.json");

interface TreeNode {
    id: number;
    label: string;
    hidden: boolean;
    children: number[];
    collapsed: boolean;
}

interface Edge {
    from: number;
    to: number;
}

function generateNodes(data: any[]): [TreeNode[], Edge[]] {
    function visit(nodes: TreeNode[], edges: Edge[], node: any) {
        let n: TreeNode = {
            id: node.id as number,
            label: node.name as string,
            hidden: false,
            collapsed: false,
            children: node.nodes.map((n: any) => n.id) as number[]
        };
        nodes.push(n);
        n.children.forEach(i => edges.push({ from: n.id, to: i }));
        node.nodes.forEach((n: any) => visit(nodes, edges, n));
    }
    let nodes: TreeNode[] = [];
    let edges: Edge[] = [];
    data.forEach((v: any) => visit(nodes, edges, v));
    return [nodes, edges];
}

let result = generateNodes(tree);
let nodes = result[0];
let edges = result[1];

let nodeSet = new vis.DataSet(nodes);
let edgeSet = new vis.DataSet(edges);

let container = document.getElementById("root");
let data = {
    nodes: nodeSet,
    edges: edgeSet
};
let options = {};
let network = new vis.Network(container, data, options);

let nodeDict: { [id: number]: TreeNode } = {};
nodes.forEach((n: TreeNode) => {
    nodeDict[n.id] = n;
});

network.on("click", function (e: any) {
    let toUpdate: any = [];
    function toggle(node: TreeNode, hidden: boolean) {
        node.hidden = hidden;
        toUpdate.push({ id: node.id, hidden: node.hidden });
        if (!node.collapsed) {
            node.children.forEach((id: number) => toggle(nodeDict[id], hidden));
        }
    }
    e.nodes.map((id: number) => nodeDict[id])
        .forEach((n: TreeNode) => {
            n.collapsed = !n.collapsed;
            n.children.forEach((id: number) => toggle(nodeDict[id], n.collapsed));
        });

    nodeSet.update(toUpdate);
    network.fit();
});
