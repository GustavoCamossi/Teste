package com.example.gustavocamossi.vidafit1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gustavocamossi.vidafit1.R;
import com.example.gustavocamossi.vidafit1.model.ItemPedido;
import com.example.gustavocamossi.vidafit1.model.Pedido;

import java.util.ArrayList;
import java.util.List;


public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.MyViewHolder> {

    private List<Pedido> pedidos;

    public AdapterPedido(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedidos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Pedido pedido = pedidos.get(i);
        holder.nome.setText(pedido.getNome());
        holder.rua.setText("Endereço: " + pedido.getRua());
        holder.cidade.setText("Cidade: " + pedido.getCidade());
        holder.numero.setText("Número: " + pedido.getNumero());
        holder.estado.setText("Estado: "+ pedido.getEstado());
        holder.bairro.setText("Bairro: " + pedido.getBairro());
        holder.cep.setText("Cep: " + pedido.getCep());
        holder.observacao.setText("Obs: " + pedido.getObservacao());

        List<ItemPedido> itens = new ArrayList<>();
        itens = pedido.getItens();
        String descricaoItens = "";

        int numeroItem = 1;
        Double total = 0.0;
        for (ItemPedido itemPedido : itens) {

            int qtde = itemPedido.getQuantidade();
            Double preco = itemPedido.getPreco();
            total += (qtde * preco);

            String nome = itemPedido.getNomeProduto();
            descricaoItens += numeroItem + ") " + nome + " / (" + qtde + " x R$ " + preco + ") \n";
            numeroItem++;
        }
        descricaoItens += "Total: R$ " + total;
        holder.itens.setText(descricaoItens);

        int metodoPagamento = pedido.getMetodoPagamento();
        String pagamento = metodoPagamento == 0 ? "Dinheiro" : "Máquina cartão";
        holder.pgto.setText("pgto: " + pagamento);

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView rua;
        TextView pgto;
        TextView observacao;
        TextView itens;
        TextView cidade;
        TextView numero;
        TextView estado;
        TextView bairro;
        TextView cep;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textPedidoNome);
            rua = itemView.findViewById(R.id.textRua);
            cidade = itemView.findViewById(R.id.textCidade);
            numero = itemView.findViewById(R.id.textNumero);
            estado = itemView.findViewById(R.id.textEstado);
            bairro = itemView.findViewById(R.id.textBairro);
            cep = itemView.findViewById(R.id.textCep);
            pgto = itemView.findViewById(R.id.textPedidoPgto);
            observacao = itemView.findViewById(R.id.textPedidoObs);
            itens = itemView.findViewById(R.id.textPedidoItens);
        }
    }

}
